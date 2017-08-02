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
import org.springframework.dao.DataAccessException;
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

import com.challenge.domain.ChallengeException;
import com.challenge.domain.Message;
import com.challenge.domain.People;
import com.challenge.domain.Popular;

@Repository
public class PeopleRepositoryImpl implements PeopleRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
	    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	private static final String SQL_INSERT_NEW_USER_INTO_PEOPLE = "insert into people(id, handle, name) values (:id, :handle, :name)";
	private static final String SQL_FIND_USERS_IN_APPLICATION = "select * from people order by id";
	private static final String SQL_FIND_MESSAGES_CURRENT_USER= "select * from messages m join people p on m.person_id=p.id where p.id =(select id from people where handle=:handle)";
	private static final String SQL_FIND_MESSAGES_CURRENT_USER_WITH_SEARCH_PARAMETER= "select * from messages m join people p on m.person_id=p.id where p.id =:id and m.content LIKE :search";
	private static final String SQL_FIND_FOLLOWERS_OF_USER= "select p.handle, p.name from people p join followers f on p.id = f.follower_person_id where f.person_id =(select id from people where handle=:handle)";
	private static final String SQL_FIND_FOLLOWING_USER= "select p.handle, p.name from people p join followers f on p.id = f.person_id where f.follower_person_id =(select id from people where handle=:handle)";
	private static final String SQL_START_FOLLOWING= "insert into followers(person_id, follower_person_id) values ((select id from people where handle=:handle), :follower_person_id)";
	private static final String SQL_UNFOLLOW_USER= "delete from followers where person_id=(select id from people where handle=:handle) and follower_person_id=:follower_person_id";
		
	private static final String SQL_FIND_POPULAR_FOLLOWER= "WITH X AS (select A.PERSON_ID, A.FOLLOWER_PERSON_ID, (SELECT COUNT(B.FOLLOWER_PERSON_ID) from followers B WHERE B.PERSON_ID = A.FOLLOWER_PERSON_ID) AS n FROM followers A ORDER BY A.PERSON_ID)" + 
			" SELECT X.PERSON_ID, X.FOLLOWER_PERSON_ID FROM X" + 
			" (SELECT person_id, max(N) maxN FROM X group by person_id ) AS Y" + 
			" WHERE X.PERSON_ID= Y.person_id and X.N = Y.maxN";
	
	@Override
	public List<People> findAll() {
		List<People> ppl =  namedParameterJdbcTemplate.query(SQL_FIND_USERS_IN_APPLICATION, new CustomerRowMapper());
		return ppl;
	}
	
	@Override
	public List<Message> findAllMessages(String handle, String search){
		List<Message> msgs;
		Map<String,Object> params = new HashMap<>();
		params.put("handle", handle);
		if(StringUtils.isEmpty(search)) {
			msgs =  (List<Message>) namedParameterJdbcTemplate.query(SQL_FIND_MESSAGES_CURRENT_USER, params, new MessagesRowMapper());
		}
		else {
			params.put("search", "%"+search+"%");
			msgs =  (List<Message>) namedParameterJdbcTemplate.query(SQL_FIND_MESSAGES_CURRENT_USER_WITH_SEARCH_PARAMETER, params, new MessagesRowMapper());
		}
		return msgs;
	}
	
	@Override
	public People save(People people) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", people.getId());
		params.addValue("handle", people.getHandle());
		params.addValue("name", people.getName());
		
		KeyHolder holder = new GeneratedKeyHolder();
		int rows = namedParameterJdbcTemplate.update(SQL_INSERT_NEW_USER_INTO_PEOPLE, params, holder);
		
		if(rows==1) {
			people.setId((Long)holder.getKey());
			return people;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findAllFollowers(String handle){
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("handle", handle);
		List<Map<String, Object>> followers =  namedParameterJdbcTemplate.queryForList(SQL_FIND_FOLLOWERS_OF_USER, params);
		return followers;
	}
	
	@Override
	public List<Map<String, Object>> findAllFollowing(String handle){
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("handle", handle);
		List<Map<String, Object>> following =  namedParameterJdbcTemplate.queryForList(SQL_FIND_FOLLOWING_USER,params);
		return following;
	}
	
	@Override
	public void follow(String handle, Long follower_person_id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("handle", handle);
		params.addValue("follower_person_id", follower_person_id);
		namedParameterJdbcTemplate.update(SQL_START_FOLLOWING, params);
	}
	
	@Override
	public void unfollow(String handle, Long follower_person_id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("handle", handle);
		params.addValue("follower_person_id", follower_person_id);
		namedParameterJdbcTemplate.update(SQL_UNFOLLOW_USER, params);
	}
	
	@Override
	public List<Popular> popular() throws ChallengeException{
		try {
			List<Popular> followers =  namedParameterJdbcTemplate.query(SQL_FIND_POPULAR_FOLLOWER, new PopularRowMapper());
			return followers;
		} catch (DataAccessException e) {
			throw new ChallengeException("Exception in popular -> "+e.getMessage());
		}
	}
	
	private class CustomerRowMapper implements RowMapper<People>{
		@Override
		public People mapRow(ResultSet rs, int row) throws SQLException {
			return new People(rs.getLong("id"), rs.getString("handle"), rs.getString("name"));
		}
	}
	
	private class MessagesRowMapper implements RowMapper<Message>{
		@Override
		public Message mapRow(ResultSet rs, int row) throws SQLException {
			return new Message(rs.getLong("id"), rs.getInt("person_id"), rs.getString("content"));
		}
	}
	
	private class PopularRowMapper implements RowMapper<Popular>{
		@Override
		public Popular mapRow(ResultSet rs, int row) throws SQLException {
			return new Popular(rs.getInt("person_id"),rs.getInt("FOLLOWER_PERSON_ID"));
		}
	}

}
