package com.challenge.repo;

public interface UserRepository {

	boolean authenticate(String name, String password);
	
}
