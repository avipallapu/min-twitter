package com.challenge.controllers;

import java.util.List;
import java.util.Map;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.domain.ChallengeException;
import com.challenge.domain.Message;
import com.challenge.domain.People;
import com.challenge.domain.Popular;
import com.challenge.repo.PeopleRepository;

@RestController
public class PeopleController {

	@Autowired
	PeopleRepository peopleRepository;
	
	private static final Logger logger= LoggerFactory.getLogger(PeopleController.class);
	
	@Context
	protected SecurityContext sc;
	
	public void setUserRepository(PeopleRepository userRepository) {
		this.peopleRepository = userRepository;
	}


	// Get all users in the application
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public List<People> getUsers(){
		try {
			return peopleRepository.findAll();
		} catch (Exception e) {
			logger.debug("Exception while processing getUsers()",e.getMessage());
		}
		return null;
	}
	
	//Read message list of current user, with search parameter
	@RequestMapping(value="/messages", method=RequestMethod.GET)
	public List<Message> getMessages( @QueryParam("search") String search){
		return peopleRepository.findAllMessages(getHandle(), search);
	}
	
	//Followers of the current user
	@RequestMapping(value="/followers", method=RequestMethod.GET)
	public List<Map<String, Object>> getFollowers(){
		return peopleRepository.findAllFollowers(getHandle());
	}
	
	//People the current user follows
	@RequestMapping(value="/following", method=RequestMethod.GET)
	public List<Map<String, Object>> getFollowing(){
		return peopleRepository.findAllFollowing(getHandle());
	}
	
	//Add new user 
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public void createUser(@RequestBody People people ){
		People new_one = new People();
		new_one=people;
		System.out.println(new_one.toString());
		peopleRepository.save(new_one);
	}
	
	// Start following another user
	@RequestMapping(value="/follow/{person_id}", method=RequestMethod.POST)
	public void followUser(@PathVariable(value="person_id") Long person_id ){
		peopleRepository.follow(getHandle(), person_id);
	}
	
	
	// unfollow another user
	@RequestMapping(value="/unfollow/{person_id}", method=RequestMethod.DELETE)
	public void unFollowUser(@PathVariable(value="person_id") Long person_id ){
		peopleRepository.unfollow(getHandle(), person_id);
	}
	
	// popular follower
	@RequestMapping(value="/popular", method=RequestMethod.GET)
	public List<Popular> popularList(){
		try {
			return peopleRepository.popular();
		} catch (ChallengeException e) {
			logger.debug(e.getMessage());
		}
		return null;
	}
	
	private String getHandle() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		        return (String) auth.getPrincipal();
		}
		return null;
	}
	
}
