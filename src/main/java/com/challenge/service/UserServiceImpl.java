package com.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.domain.User;
import com.challenge.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
	@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		return userRepository.findOneByUsernameAndPassword(username, password);
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findOneByUsername(username);
	}

	@Override
	public void fillData() {
		System.out.println("dasdsada");
		
	}

}
