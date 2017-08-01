package com.challenge.domain;

public class Message {
	private Long id;
	
	private int person_id;
	
	private String content;
	
	public Message() {
		
	}
	
	public Message(Long id, int person_id, String content) {
		this.id = id;
		this.person_id = person_id;
		this.content = content;
	}
	
	public Message(int handle, String name) {
		super();
		this.person_id = handle;
		this.content = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPerson_id() {
		return person_id;
	}

	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
