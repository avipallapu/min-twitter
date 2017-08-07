package com.challenge.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.challenge.domain.TwitterException;
import com.challenge.domain.Popular;

@Repository
public class TwitterRepositoryImpl implements TwitterRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
	    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	private static final String SQL_FIND_FOLLOWERS_OF_USER= "select p.id,p.handle, p.name from people p join followers f on p.id = f.follower_person_id where f.person_id =(select id from people where handle=:handle)";
	private static final String SQL_FIND_FOLLOWING_USER= "select p.id,p.handle, p.name from people p join followers f on p.id = f.person_id where f.follower_person_id =(select id from people where handle=:handle)";
	private static final String SQL_START_FOLLOWING= "insert into followers(person_id, follower_person_id) values ((select id from people where handle=:handle), :follower_person_id)";
	private static final String SQL_UNFOLLOW_USER= "delete from followers where person_id=(select id from people where handle=:handle) and follower_person_id=:follower_person_id";
		
	private static final String SQL_FIND_POPULAR_FOLLOWER= "WITH X AS ( select A.PERSON_ID, A.FOLLOWER_PERSON_ID, (SELECT COUNT(B.FOLLOWER_PERSON_ID) from followers B WHERE B.PERSON_ID = A.FOLLOWER_PERSON_ID) AS n FROM followers A ORDER BY A.PERSON_ID )" + 
			" SELECT X.PERSON_ID, X.FOLLOWER_PERSON_ID FROM X," + 
			" (SELECT person_id, max(N) maxN FROM X group by person_id ) AS Y" + 
			" WHERE X.PERSON_ID= Y.person_id and X.N = Y.maxN";
	
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
	public void follow(String handle, Integer follower_person_id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("handle", handle, Types.VARCHAR);
		params.addValue("follower_person_id", follower_person_id, Types.INTEGER);
		namedParameterJdbcTemplate.update(SQL_START_FOLLOWING, params);
	}
	
	@Override
	public void unfollow(String handle, Integer follower_person_id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("handle", handle,Types.VARCHAR);
		params.addValue("follower_person_id", follower_person_id, Types.INTEGER);
		namedParameterJdbcTemplate.update(SQL_UNFOLLOW_USER, params);
	}
	
	@Override
	public List<Popular> popular() throws TwitterException{
		try {
			List<Popular> followers =  namedParameterJdbcTemplate.query(SQL_FIND_POPULAR_FOLLOWER, new PopularRowMapper());
			return followers;
		} catch (DataAccessException e) {
			throw new TwitterException("Exception in popular -> "+e.getMessage());
		}
	}
	
	private class PopularRowMapper implements RowMapper<Popular>{
		@Override
		public Popular mapRow(ResultSet rs, int row) throws SQLException {
			return new Popular(rs.getInt("person_id"), rs.getInt("FOLLOWER_PERSON_ID"));
		}
	}
}
