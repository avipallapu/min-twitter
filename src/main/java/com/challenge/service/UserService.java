package com.challenge.service;

import com.challenge.domain.User;

public interface UserService {

	public User findUserByUsernameAndPassword(String username, String password);

	public User findUserByUsername(String username);

	public void fillData();

}
