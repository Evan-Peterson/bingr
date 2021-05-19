package bingr.movies;

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

@Api(value = "MovieController")
@RestController
public class MovieController {

	
		@Autowired
		MovieRepository movieRepo;
		
		private String success = "{\"message\":\"success\"}";
	    private String failure = "{\"message\":\"failure\"}";
	    
	    @ApiOperation(value = "Returns a list of all movies")
	    @GetMapping(path = "/movies")
	    public List<Movie> getAllMovies() {
	    	return movieRepo.findAll();
	    }
	    
	    @ApiOperation(value = "Return the amount of likes a movie has")
	    @GetMapping(path = "/movies/{movieId}")
	    public int getLikes(@PathVariable String movieId) {
	    	
	    	Movie movie = null;
	    	
//	    	try {
//	    		movie = movieRepo.findByMovieId(movieId);
//	    	} catch (Exception e) {
//	    		return -1;
//	    	}
	    	
	    	movie = movieRepo.findByMovieId(movieId);
	    	
	    	if(movie == null) {
	    		return 0;
	    	}
	    	
	    	return movie.getLikes();
	    }
}
