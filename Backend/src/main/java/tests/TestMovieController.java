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
import bingr.movies.*;


public class TestMovieController {
	
	@Mock
	MovieRepository movieRepo;
	
	@InjectMocks
	MovieController movieController;
	
	@SuppressWarnings("deprecation")
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void createMovieTest() {
		when(movieRepo.findByMovieId("22")).thenReturn(new Movie("title", "22", "rating", 1));
		
		Movie movie = movieRepo.findByMovieId("22");
		
		assertEquals("title", movie.getTitle());
		assertEquals("22", movie.getMovieId());
	}
	
	@Test
	public void getLikesTest() {
		when(movieRepo.findByMovieId("22")).thenReturn(new Movie("title", "22", "rating", 1));
		
		Movie movie = movieRepo.findByMovieId("22");
		movie.addLike();
		movieRepo.save(movie);
		
		assertEquals(2, movie.getLikes());
	}
	
	@Test
	public void getAndSetRatingTest() {
		when(movieRepo.findByMovieId("42")).thenReturn(new Movie("Godzilla v Kong", "42", "PG-13", 50));
		Movie movie = movieRepo.findByMovieId("42");
		
		assertEquals(movie.getRating(), "PG-13");
		
		movie.setRating("R");
		
		assertEquals(movie.getRating(), "R");
	}
	
	@Test
	public void getAndSetTitleTest() {
		when(movieRepo.findByMovieId("42")).thenReturn(new Movie("Godzilla v Kong", "42", "PG-13", 50));
		Movie movie = movieRepo.findByMovieId("42");
		
		assertEquals(movie.getTitle(), "Godzilla v Kong");
		
		movie.setTitle("Mortal Kombat");
		
		assertEquals(movie.getTitle(), "Mortal Kombat");
	}
}
