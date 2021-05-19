package bingr.movies;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Movie {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@ApiModelProperty(notes = "Title of the movie")
	private String title;
	
	@ApiModelProperty(notes = "A unique ID for each movie")
	private String movieId;
	 
	@ApiModelProperty(notes = "The rating out of 10 for the movie")
	private String rating;
	
	@ApiModelProperty(notes = "Amount of likes a movie has")
	private int likes;
	
	public Movie(String title, String id, String rating, int likes) {
		this.title = title;
		this.movieId = id;
		this.rating = rating;
		this.likes = likes;
	}
	
	public Movie() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String id) {
		this.movieId = id;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public int getLikes() {
		return likes;
	}
	
	public void addLike() {
		likes++;
	}
}
