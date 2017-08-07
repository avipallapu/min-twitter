package com.challenge.service;

import java.util.List;

import com.challenge.domain.People;
import com.challenge.domain.User;

public interface UserService {

	public User findUserByUsernameAndPassword(String username, String password);

	public User findUserByUsername(String username);

	public List<People> findAll();

	public boolean checkPersonExists(Long person_id);

	public boolean personIdPersonAlreadyFollowing(String handle, Long person_id);
}
