package com.challenge.service;

import java.util.List;
import java.util.Map;

import com.challenge.domain.Popular;
import com.challenge.domain.TwitterException;

public interface TwitterService {
	
	List<Map<String, Object>> findAllFollowers(String username);

	List<Map<String, Object>> findAllFollowing(String handle);

	void follow(String handle, Integer person_id);

	void unfollow(String handle, Integer person_id);

	List<Popular> popular() throws TwitterException;

}
