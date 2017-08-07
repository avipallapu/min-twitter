package com.challenge.repo;

import java.util.List;

import com.challenge.domain.People;
import com.challenge.domain.User;

public interface UserRepository {

	User findOneByUsernameAndPassword(String username, String password);

	User findOneByUsername(String username);

	List<People> findAll();

	boolean checkPersonExists(Long person_id);

	People save(People people);

	User getUserById(int id);

	boolean personIdPersonAlreadyFollowing(String handle, Long person_id);
	
}
