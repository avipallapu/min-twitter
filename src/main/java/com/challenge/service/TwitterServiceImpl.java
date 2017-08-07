package com.challenge.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.domain.Popular;
import com.challenge.domain.TwitterException;
import com.challenge.repo.TwitterRepository;

@Service
public class TwitterServiceImpl implements TwitterService{

	@Autowired
	private TwitterRepository twitterRepository;
	
	@Override
	public List<Map<String, Object>> findAllFollowers(String username) {
		return twitterRepository.findAllFollowers(username);
	}

	@Override
	public List<Map<String, Object>> findAllFollowing(String handle) {
		return twitterRepository.findAllFollowing(handle);
	}

	@Override
	public void follow(String handle, Integer person_id) {
		twitterRepository.follow(handle, person_id);
	}

	@Override
	public void unfollow(String handle, Integer person_id) {
		twitterRepository.unfollow(handle, person_id);
	}

	@Override
	public List<Popular> popular() throws TwitterException {
		return twitterRepository.popular();
	}
	
}
