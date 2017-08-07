package com.challenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.domain.Message;
import com.challenge.repo.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessageRepository messageRepository;
	
	@Override
	public List<Message> findAllMessages(String username, String search) {
		return messageRepository.findAllMessages(username, search);
	}

}
