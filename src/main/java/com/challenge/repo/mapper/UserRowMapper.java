package com.challenge.repo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.challenge.domain.People;

public class UserRowMapper  implements RowMapper<People>{
	
	public static final String SQL_FIND_USERS_IN_APPLICATION = "select * from people order by id";
	public static final String SQL_INSERT_NEW_USER_INTO_PEOPLE = "insert into people(id, handle, name) values (:id, :handle, :name)";
	public static final String SQL_FIND_IF_USER_EXISTS_IN_APPLICATION = "select * from people where id=:id";
	public static final String SQL_FIND_IF_USER_ALREADY_FOLLOWING = "select * from FOLLOWERS where person_id=:person_id and follower_person_id=(SELECT id from people where handle=:handle)";
	
	@Override
	public People mapRow(ResultSet rs, int row) throws SQLException {
		return new People(rs.getLong("id"), rs.getString("handle"), rs.getString("name"));
	}
}
