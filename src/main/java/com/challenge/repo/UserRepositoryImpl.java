package com.challenge.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.challenge.domain.People;
import com.challenge.domain.User;
import com.challenge.repo.mapper.UserRowMapper;

@Repository
public class UserRepositoryImpl implements UserRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
	    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	private static final String SQL_CHECK_LOGIN_USERNAME_PASSWORD = "select user_name, password from login where user_name=:user_name and password=:password";
	private static final String SQL_CHECK_LOGIN_USERNAME_ONLY = "select user_name,password from login where user_name=:user_name";

	private static final class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException         {
            return new User(rs.getString("user_name"), rs.getString("password"));
        }
    }

	@Override
	public User findOneByUsernameAndPassword(String username, String password) {
		List<User> result;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("user_name", username);
		params.addValue("password", password);
		result = namedParameterJdbcTemplate.query(SQL_CHECK_LOGIN_USERNAME_PASSWORD, params, new UserMapper());
		if(result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

	@Override
	public User findOneByUsername(String username) {
		List<User> result;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("user_name", username);
		result = namedParameterJdbcTemplate.query(SQL_CHECK_LOGIN_USERNAME_ONLY, params, new UserMapper());
		if(result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}
	
	@Override
	public List<People> findAll(){
		List<People> ppl =  namedParameterJdbcTemplate.query(UserRowMapper.SQL_FIND_USERS_IN_APPLICATION, new UserRowMapper());
		return ppl;
	}
	
	@Override
	public People save(People people) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", people.getId());
		params.addValue("handle", people.getHandle());
		params.addValue("name", people.getName());
		
		KeyHolder holder = new GeneratedKeyHolder();
		int rows = namedParameterJdbcTemplate.update(UserRowMapper.SQL_INSERT_NEW_USER_INTO_PEOPLE, params, holder);
		
		if(rows==1) {
			people.setId((Long)holder.getKey());
			return people;
		}
		return null;
	}
	
	@Override
	public boolean checkPersonExists(Long person_id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", person_id);
		List<People> followers =  namedParameterJdbcTemplate.query(UserRowMapper.SQL_FIND_IF_USER_EXISTS_IN_APPLICATION, params, new UserRowMapper());
		if(followers==null || followers.isEmpty()) {
			return false;
		}
		return true;
		
	}

	@Override
	public User getUserById(int id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		List<People> followers =  namedParameterJdbcTemplate.query(UserRowMapper.SQL_FIND_IF_USER_EXISTS_IN_APPLICATION, params, new UserRowMapper());
		if(followers==null || followers.isEmpty()) {
			return null;
		}
		return (User) followers;
	}

	@Override
	public boolean personIdPersonAlreadyFollowing(String handle, Long person_id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("person_id", person_id);
		params.addValue("handle", handle);
		List<People> followers =  namedParameterJdbcTemplate.query(UserRowMapper.SQL_FIND_IF_USER_ALREADY_FOLLOWING, params, new UserRowMapper());
		if(followers==null || followers.isEmpty()) {
			return false;
		}
		return true;
	}
	
	
}
