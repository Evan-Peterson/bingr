package coms309;

import java.util.Arrays;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import coms309.people.PeopleController;
import coms309.people.Person;

/**
 * Simple Hello World Controller to display the string returned
 *
 * @author Vivek Bengre
 */

@RestController
class WelcomeController {
	
	PeopleController cont = new PeopleController();
	
//    @GetMapping("/")
//    public HashMap<String, Person> welcome() {
//        return "Hello and welcome to hello people";
////    	return PeopleController.peopleList;
//    }
}
