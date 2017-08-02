package com.challenge.repo;

import java.util.List;
import java.util.Map;

import com.challenge.domain.ChallengeException;
import com.challenge.domain.Message;
import com.challenge.domain.People;
import com.challenge.domain.Popular;

public interface PeopleRepository {
	
	People save(People cust);
	
	List<People> findAll();
	
	List<Message> findAllMessages(String username, String search);

	List<Map<String, Object>> findAllFollowers(String username);

	List<Map<String, Object>> findAllFollowing(String handle);

	void follow(String handle, Long person_id);

	void unfollow(String handle, Long person_id);

	List<Popular> popular() throws ChallengeException;
	
}
