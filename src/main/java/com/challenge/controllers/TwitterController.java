package com.challenge.controllers;

import java.util.List;
import java.util.Map;

import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.domain.TwitterException;
import com.challenge.domain.ErrorResponse;
import com.challenge.domain.Message;
import com.challenge.domain.People;
import com.challenge.domain.Popular;
import com.challenge.service.MessageService;
import com.challenge.service.TwitterService;
import com.challenge.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class TwitterController {

	@Autowired
	TwitterService twitterService;
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	UserService userService;
	
	private static final Logger logger= LoggerFactory.getLogger(TwitterController.class);
	
	// Get all users in the application
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ResponseEntity<List<People>> getUsers() throws TwitterException{
		try {
			return new ResponseEntity<List<People>>(userService.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception while processing getUsers()",e.getMessage());
			throw new TwitterException("Internal Server Error, Please contact Customer care");
		}
	}
	
	//Read message list of current user, with search parameter
	@RequestMapping(value="/messages", method=RequestMethod.GET)
	public ResponseEntity<List<Message>> getMessages( @QueryParam("search") String search) throws TwitterException{
		try {
			return new ResponseEntity<List<Message>>(messageService.findAllMessages(getHandle(), search), HttpStatus.OK);
		}
		catch (Exception e) {
			logger.debug("Exception while processing getMessages()",e.getMessage());
			throw new TwitterException("Internal Server Error, Please contact Customer care");
		}
	}
	
	//Followers of the current user
	@RequestMapping(value="/followers", method=RequestMethod.GET)
	public List<Map<String, Object>> getFollowers() throws TwitterException{
		try {
			return twitterService.findAllFollowers(getHandle());
		}
		catch (Exception e) {
			logger.debug("Exception while processing getFollowers()",e.getMessage());
			throw new TwitterException("Internal Server Error, Please contact Customer care");
		}
	}
	
	//People the current user follows
	@RequestMapping(value="/following", method=RequestMethod.GET)
	public List<Map<String, Object>> getFollowing() throws TwitterException{
		try{
			List<Map<String, Object>> xx =  twitterService.findAllFollowing(getHandle());
			for(Map<String, Object> x : xx) {
				for(Object x1 : x.values()) {
					System.out.println(x1.toString());
				}
			}
		}
		catch (Exception e) {
			logger.debug("Exception while processing getFollowing()",e.getMessage());
			throw new TwitterException("Internal Server Error, Please contact Customer care");
		}
		return null;
	}
	
	// Start following another user
	@RequestMapping(value="/follow/{person_id}", method=RequestMethod.POST)
	public void followUser(@PathVariable(value="person_id") Integer person_id ) throws TwitterException{
//		if(person_id<1) {
//			throw new TwitterException("Invalid person_id to follow");
//		}
//		if(!personIdExists(person_id)) {
//			throw new TwitterException("Person does not exist in the system");
//		}
//		if(personIdPersonAlreadyFollowing(person_id)) {
//			throw new TwitterException("Already following the user");
//		}
		try{
			twitterService.follow(getHandle(), person_id);
		}
		catch (Exception e) {
			logger.debug("Exception while processing followUser()",e.getMessage());
			throw new TwitterException("Internal Server Error, Please contact Customer care");
		}
	}

	// unfollow another user
	@RequestMapping(value="/unfollow/{person_id}", method=RequestMethod.DELETE)
	public void unFollowUser(@PathVariable(value="person_id") Integer person_id ) throws TwitterException{
		try{
			getFollowing();
			twitterService.unfollow(getHandle(), person_id);
			System.out.println("**********************************");
			getFollowing();
		}
		catch (Exception e) {
			logger.debug("Exception while processing unFollowUser()",e.getMessage());
			throw new TwitterException("Internal Server Error, Please contact Customer care");
		}
	}
	
	// popular follower
	@RequestMapping(value="/popular", method=RequestMethod.GET)
	public ResponseEntity<List<Popular>> popularList() throws TwitterException{
		try {
			return new ResponseEntity<List<Popular>>(twitterService.popular(), HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Exception while processing popularList()",e.getMessage());
			throw new TwitterException("Internal Server Error, Please contact Customer care");
		}
	}
	
	
	
	private boolean personIdExists(Long person_id) throws TwitterException {
		try{
			return userService.checkPersonExists(person_id);
		}
		catch (Exception e) {
			logger.debug("Exception while processing getFollowers()",e.getMessage());
			throw new TwitterException("Internal Server Error, Please contact Customer care");
		}
	}
	
	private boolean personIdPersonAlreadyFollowing(Long person_id) throws TwitterException {
		try{
			return userService.personIdPersonAlreadyFollowing(getHandle(), person_id);
		}
		catch (Exception e) {
			logger.debug("Exception while processing getFollowers()",e.getMessage());
			throw new TwitterException("Internal Server Error, Please contact Customer care");
		}
	}
	
	private String getHandle() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		        return (String) auth.getPrincipal();
		}
		return null;
	}
	
	@ExceptionHandler(TwitterException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    }
}
