package com.challenge.repo;

import com.challenge.domain.User;

public interface UserRepository {

	User findOneByUsernameAndPassword(String username, String password);

	User findOneByUsername(String username);
	
}
