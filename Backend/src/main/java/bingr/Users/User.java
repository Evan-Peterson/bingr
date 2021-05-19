package bingr.Users;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import bingr.movies.Movie;
import io.swagger.annotations.ApiModelProperty;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ApiModelProperty(notes = "User name of user")
    private String name;
    
    @ApiModelProperty(notes = "Email of user **UNIQUE**")
    private String emailId;
    
    @ApiModelProperty(notes = "First Name")
    private String firstName;
    
    @ApiModelProperty(notes = "Last Name")
    private String surname;
    
    @ApiModelProperty(notes = "User's Password")
    private String password;
    
    @ApiModelProperty(notes = "User's Bio")
    private String bio = "";
    
    @ApiModelProperty(notes = "A list of movie IDs that a user has liked")
    @ManyToMany
	private List<Movie> likes;
    
    @ApiModelProperty(notes = "List of friends")
    @ManyToMany
	@JsonIgnore
    private List<User> friends;	

    
    public User(String name, String emailId, String firstname, String surname, String password, String bio) {
        this.name = name;
        this.emailId = emailId;
        this.firstName = firstname;
        this.surname = surname;
        this.password = password;
        this.bio += bio;
        likes = new ArrayList<>();
        friends = new ArrayList<>();
    }

    public User() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmailId(){
        return emailId;
    }

    public void setEmailId(String emailId){
        this.emailId = emailId;
    }
    
    public void setFirstName(String firstname) {
    	this.firstName = firstname;
    }
    
    public String getFirstName() {
    	return firstName;
    }
    
    public void setSurname(String surname) {
    	this.surname = surname;
    }
    
    public String getSurname() {
    	return surname;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    /**
     * gross plain text password, this is bad but..
     * @return
     */
    public String getPassword() {
    	return password;
    }

    public String getBio() {
    	return bio;
    }
    
    public void setBio(String bio) {
    	this.bio = bio;
    }
    
    public void addLike(Movie id) {
		likes.add(id);
	}
    
    public List<Movie> getLikes() {
		return likes;
	}
    
    public void addFriend(User friend) {
    	friends.add(friend);
    }
    
    public List<User> getFriends() {
    	return friends;
    }
	
}
