package tests;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import bingr.Users.*;
import bingr.movies.Movie;


public class TestUserController {
	
	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserController userController;
	
	@SuppressWarnings("deprecation")
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getUserByEmailIdTest() {
		when(userRepository.findByEmailId("evanp@iastate.edu")).thenReturn(new User("Evan", "evanp@iastate.edu", "evan", "peterson", "pass", "my bio"));
		
		User user = userRepository.findByEmailId("evanp@iastate.edu");
		
		assertEquals("Evan", user.getName());
		assertEquals("pass", user.getPassword());
	}
	
	@Test
	public void createUserTest() {
		
		User user = new User("Kyle", "kjriggs@iastate.edu", "Kyle", "Riggs", "password", "kyle's bio");
		
		when(userController.createUser(user)).thenReturn(userController.createUser(user));
		
		assertEquals("kyle's bio", user.getBio());
		assertEquals("kyle's bio", user.getBio());
	}
	
	@Test
	public void addFriendsTest() {
		
		when(userRepository.findByEmailId("kjriggs@iastate.edu")).thenReturn(new User("Kyle", "kjriggs@iastate.edu", "Kyle", "Riggs", "password", "kyle's bio"));
		when(userRepository.findByEmailId("friend@iastate.edu")).thenReturn(new User("Friend", "friend@iastate.edu", "Friend", "Buddy", "123", "kyle's friend"));
		
		User user = userRepository.findByEmailId("kjriggs@iastate.edu");
		
		userController.addFriends("kjriggs@iastate.edu", "friend@iastate.edu");
		
		assertEquals(user.getFriends(), "friend@iastate.edu-");
	}
	
	@Test
	public void updateBioTest() {
		
		when(userRepository.findByEmailId("kjriggs@iastate.edu")).thenReturn(new User("Kyle", "kjriggs@iastate.edu", "Kyle", "Riggs", "password", "kyle's bio"));
		
		User user = userRepository.findByEmailId("kjriggs@iastate.edu");
		
		assertEquals(user.getBio(), "kyle's bio");
		
		userController.updateBio("kjriggs@iastate.edu", "new bio");
		
		assertEquals(user.getBio(), "new bio");
	}
	
//	@Test
//	public void getLikesTest() {
//		
//		when(userRepository.findByEmailId("kjriggs@iastate.edu")).thenReturn(new User("Kyle", "kjriggs@iastate.edu", "Kyle", "Riggs", "password", "kyle's bio"));
//		
//		assertEquals("100", userController.getLikes("kjriggs@iastate.edu"));
//	}
	
	
	@Test
	public void getUsersTest() {
		List<User> test = new ArrayList<User>();
		
		User user1 = new User("Kyle", "kjriggs@iastate.edu", "Kyle", "Riggs", "password", "kyle bio");
		User user2 = new User("Evan", "evanp@iastate.edu", "evan", "peterson", "pass", "my bio");
		User user3 = new User("andrew", "akfrank@iastate.edu", "andrew", "frank", "123", "andrew bio");

		
		test.add(user1);
		test.add(user2);
		test.add(user3);
		
		when(userController.getAllUsers()).thenReturn(test);
		
		assertEquals(3, test.size());
	}
	
	@Test
	public void addLikeTest() {
		User user = new User("Evan", "evanp@iastate.edu", "evan", "peterson", "pass", "my bio");
		
		when(userController.createUser(user)).thenReturn(userController.createUser(user));
		
		userController.addLike(user.getEmailId(), "55");
		
		assertEquals("55", user.getLikes().get(0).getId());
	}
	
	
	@Test
	public void loginTest() {
//		User user = new User("Evan", "evanp@iastate.edu", "evan", "peterson", "pass", "my bio", "55", "");
		
		when(userRepository.findByEmailId("evanp@iastate.edu")).thenReturn(new User("Evan", "evanp@iastate.edu", "evan", "peterson", "pass", "my bio"));
		
		User user = userRepository.findByEmailId("evanp@iastate.edu");
		
		assertEquals("{\"message\":\"success\"}", userController.login(user.getEmailId(), user.getPassword()));
	}
	
	@Test
	public void isMatchTest() {
		
		when(userRepository.findByEmailId("evanp@iastate.edu")).thenReturn(new User("Evan", "evanp@iastate.edu", "evan", "peterson", "pass", "my bio"));
		User user = userRepository.findByEmailId("evanp@iastate.edu");
		
		when(userRepository.findByEmailId("kjriggs@iastate.edu")).thenReturn(new User("Kyle", "kjriggs@iastate.edu", "Kyle", "Riggs", "password", "kyle's bio"));
		User user2 = userRepository.findByEmailId("kjriggs@iastate.edu");
		
		Movie movie = new Movie("Title", "123", "10", 0);
		
		user.addFriend(user2);
		user2.addLike(movie);
		
		assertEquals(userController.addLike("evanp@iastate.edu", "123"), "{\"message\":\"match\":\"" + user2.getEmailId() + "\"}");
	}

}
