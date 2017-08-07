package com.challenge.service;

import java.util.List;

import com.challenge.domain.Message;

public interface MessageService {

	List<Message> findAllMessages(String username, String search);
}
