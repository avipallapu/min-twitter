package com.challenge.repo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.challenge.domain.Message;

public class MessagesRowMapper  implements RowMapper<Message>{
	
	public static final String SQL_FIND_MESSAGES_CURRENT_USER= "select * from messages m join people p on m.person_id=p.id where p.id =(select id from people where handle=:handle)";
	public static final String SQL_FIND_MESSAGES_CURRENT_USER_WITH_SEARCH_PARAMETER= "select * from messages m join people p on m.person_id=p.id where p.id =(select id from people where handle=:handle) and m.content LIKE :search";
	
	@Override
	public Message mapRow(ResultSet rs, int row) throws SQLException {
		return new Message(rs.getLong("id"), rs.getInt("person_id"), rs.getString("content"));
	}

}
