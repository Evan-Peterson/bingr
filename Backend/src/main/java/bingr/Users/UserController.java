package bingr.Users;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bingr.movies.Movie;
import bingr.movies.MovieRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



@Api(value = "UserController", description = "APIs related to the users")
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    MovieRepository movieRepository;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";
    private String userNotFound = "{\"message\":\"Error: User not found\"}";
    
    @ApiOperation(value = "Returns list of all users")
    @GetMapping(path = "/users")
	public
    List<User> getAllUsers(){
        return userRepository.findAll();
    }
    
    
    @ApiOperation(value = "Gets user by email")
    @GetMapping(path = "/users/{emailId}")
	public
    User getUserByEmailId(@PathVariable String emailId) {		
    	return userRepository.findByEmailId(emailId);
    }
    
    
    @ApiOperation(value = "Creates a new user")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "{\\\"message\\\":\\\"success\\\"}"),
    		@ApiResponse(code = 404, message = "{\\\"message\\\":\\\"failure\\\"}")
    })
    @PostMapping(path = "/users")
    public
    String createUser(@RequestBody User user){
        if (user == null) {
        	return failure;
        }
        
        // Tries to make a new user with information given
        // If the email given is non-unique then it will go into the catch
        try {
        	userRepository.save(user);
        } catch(Exception e) {
        	return "{\"message\":\"Error: Email Already Exisits\"}";
        }
        
        return success;
    }
    
    @PutMapping(path = "/users/username/{emailId}/{username}")
    String modifyUserName(@PathVariable String emailId, @PathVariable String username) {
    	
    	User user = null;
    	
    	try {
    		user = userRepository.findByEmailId(emailId);
    	} catch (Exception e) {
    		return userNotFound;
    	}
    	
    	if(user != null) {
    		user.setName(username);
    		userRepository.save(user);
    	}
    	
    	return success;
    }
    
    @ApiOperation(value = "Edits a user by ID")
    @PutMapping("/users/{id}")
    User updateUser(@PathVariable int id, @RequestBody User request){
        User user = userRepository.findById(id);
        if(user == null)
            return null;
        userRepository.save(request);
        return userRepository.findById(id);
    }   
    
    @ApiOperation(value = "Uses emailID and password to log in user")
    @GetMapping(path = "/users/login/{emailId}/{password}") 
    public String login(@PathVariable String emailId, @PathVariable String password) {
    	User user = userRepository.findByEmailId(emailId);
    	
    	if(user == null) {
    		return failure;
    	}
    	
    	if(!user.getPassword().equals(password)) {
    		return failure;
    	}
    	
    	return success;
    }
    
    @ApiOperation(value = "Deletes user by ID")
    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
        return success;
    }
    
    @PutMapping(path = "/users/bio/{emailId}/{bio}")
	public
    String updateBio(@PathVariable String emailId, @PathVariable String bio) {
    	User user = null;
    	try {
    		user = userRepository.findByEmailId(emailId);
    	} catch (Exception e){
    		return userNotFound;
    	}
    	
    	if(user != null) {
    		user.setBio(bio);
    	}
    	
    	
    	try {
    		userRepository.save(user);
    	} catch (Exception e) {
    		return userNotFound;
    	}
    	
    	return success;
    }
    
    @GetMapping(path = "/users/bio/{emailId}")
    String getBio(@PathVariable String emailId) {
    	User user = null;
    	
    	try {
    		user = userRepository.findByEmailId(emailId);
    	} catch (Exception e) {
    		return userNotFound;
    	}
    	
    	return user.getBio();
    }
    
    @PutMapping(path = "/users/like/{emailId}/{movieId}")
    public String addLike(@PathVariable String emailId, @PathVariable String movieId) {
    	
    	User user = null;
    	Movie movie = null;
    	String result = "";
    	
    	try {
    		user = userRepository.findByEmailId(emailId);
    	} catch (Exception e) {
    		return userNotFound;
    	}
    	
    	
    	
    	try {
    		movie = movieRepository.findByMovieId(movieId);
    	} catch(Exception e) {
    		movie = new Movie("", movieId, "", 1);
    		movieRepository.save(movie);
    	}
    	
    	if(movie == null) {
    		movie = new Movie("", movieId, "", 1);
    		movieRepository.save(movie);
    	}
    	
    	if(user != null) {
    		user.addLike(movie);
    		movie.addLike();
 
    		String match = isMatch(user, movieId);
    		
    		movieRepository.save(movie);
    		userRepository.save(user);
    		
    		if(!match.equals("")) {
    			result = "{\"message\":\"match\",\"friend\":\"" + match + "\"}";
    		} else {
    			result = success;
    		}
    	}
    	
    	return result;
    }
    	
    	
    
//    @GetMapping(path = "/users/like/{emailId}")
//	public
//    List<Movie> getLikes(@PathVariable String emailId) {
//    	
//    	User user = null;
//    	
//    	try {
//    		user = userRepository.findByEmailId(emailId);
//    	} catch(Exception e) {
//    		return userNotFound;
//    	}
//    	
//    	if(user != null) {
//    		return user.getLikes(); 
//    	}
//    	
//    	return failure;
//    }
    
    
    @PutMapping(path = "users/friends/{emailId}/{friendEmailId}")
	public
    String addFriends(@PathVariable String emailId, @PathVariable String friendEmailId) {
    	
    	User user = null;

    	User friend = null;
    	
    	try {
    		user = userRepository.findByEmailId(emailId);
    	} catch (Exception e) {
    		return userNotFound;
    	}
    	
    	//This try catch may not be neccessary, but does not hurt
    	try {
    		friend = getUserByEmailId(friendEmailId);
    	} catch (Exception e){
    		return userNotFound;
    	}
    	
    	if (user != null && friend != null) {
    		user.addFriend(friend);			
    		userRepository.save(user);
    		return success;
    	}
    	
    	else {
    		return failure;
    	}
    }
    
	@GetMapping(path = "users/friends/{emailId}")
	public
	List<User> listFriends(@PathVariable String emailId){
		
		User user = null;
		List<User> userFriends = null;
		
		try {
			user = userRepository.findByEmailId(emailId);
		} catch (Exception e) {
			return null;
		}
		
		if(user != null){
			userFriends = user.getFriends();
			return user.getFriends();
		}
		else{
			 return null;
		}
	}
  
    
    
    /**
     * To be used in the adding of movieID
     * Want to check every movie we add to see if it is a match or not
     * @param user user we are adding the movie to
     * @param movieID id number of movie
     * @return emailID of match, empty string if no match is found
     */
    private String isMatch(User user, String movieID) { 
    	
    	List<User> userFriends = user.getFriends();
    	
    	if(userFriends != null) {
        	for (int i = 0; i < userFriends.size(); i++) {
        		User friend = null;
        		try {
        			friend = userRepository.findByEmailId(userFriends.get(i).getEmailId());
        		} catch(Exception e) {
        			
        		}	
        		
    			if(friend != null) {
    				
    				List<Movie> friendIDs = friend.getLikes();
    				
    				for (int j = 0; j < friendIDs.size(); j++) {
    					if(friendIDs.get(j).getMovieId().equals(movieID)) {
    						return friend.getEmailId();
    					}
    				}
    			}
    		}
        }
    	return "";
    }
}
