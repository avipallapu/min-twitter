package com.challenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.domain.People;
import com.challenge.domain.User;
import com.challenge.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		return userRepository.findOneByUsernameAndPassword(username, password);
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findOneByUsername(username);
	}

	@Override
	public List<People> findAll() {
		return userRepository.findAll();
	}

	@Override
	public boolean checkPersonExists(Long person_id) {
		return userRepository.checkPersonExists(person_id);
	}

	@Override
	public boolean personIdPersonAlreadyFollowing(String handle, Long person_id) {
		return userRepository.personIdPersonAlreadyFollowing(handle, person_id);
	}

}
