package com.challenge.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.challenge.domain.Message;
import com.challenge.repo.mapper.MessagesRowMapper;

@Repository
public class MessageRepositoryImpl implements MessageRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<Message> findAllMessages(String handle, String search){
		List<Message> msgs;
		Map<String,Object> params = new HashMap<>();
		params.put("handle", handle);
		if(StringUtils.isEmpty(search)) {
			msgs =  (List<Message>) namedParameterJdbcTemplate.query(MessagesRowMapper.SQL_FIND_MESSAGES_CURRENT_USER, params, new MessagesRowMapper());
		}
		else {
			params.put("search", "%"+search+"%");
			msgs =  (List<Message>) namedParameterJdbcTemplate.query(MessagesRowMapper.SQL_FIND_MESSAGES_CURRENT_USER_WITH_SEARCH_PARAMETER, params, new MessagesRowMapper());
		}
		return msgs;
	}

}
