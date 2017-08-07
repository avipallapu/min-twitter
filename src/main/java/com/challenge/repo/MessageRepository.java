package com.challenge.repo;

import java.util.List;

import com.challenge.domain.Message;

public interface MessageRepository {

	List<Message> findAllMessages(String username, String search);
}
