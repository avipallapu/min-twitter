package com.challenge.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.challenge.domain.User;

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
	
}
