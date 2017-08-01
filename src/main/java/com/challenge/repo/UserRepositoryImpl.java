package com.challenge.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties.Jdbc;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.challenge.domain.Message;
import com.challenge.domain.People;

@Repository
public class UserRepositoryImpl implements UserRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
	    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	private static final String SQL_CHECK_LOGIN_USER = "select user_name, password from login where user_name=:user_name and password=:password";

	@Override
	public boolean authenticate(String user_name, String password) {
		List<String> result;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("user_name", user_name);
		params.addValue("password", password);
		result = namedParameterJdbcTemplate.query(SQL_CHECK_LOGIN_USER, params, new UserMapper());
		if(result.isEmpty()) {
			return false;
		}
		return true;
	}
	
	private static final class UserMapper implements RowMapper<String> {
        public String mapRow(ResultSet rs, int rowNum) throws SQLException         {
            return rs.getString("user_name");
        }
    }
	
}
