package com.challenge.domain;

public class People {
	
	private Long id;
	
	private String handle;
	
	private String name;
	
	public People() {
		
	}
	
	public People(Long id, String handle, String name) {
		this.id = id;
		this.handle = handle;
		this.name = name;
	}
	
	public People(int id, String handle, String name) {
		this.id = (long) id;
		this.handle = handle;
		this.name = name;
	}
	
	public People(String handle, String name) {
		super();
		this.handle = handle;
		this.name = name;
	}
	public People(String handle) {
		super();
		this.handle = handle;
	}

	public People(int id) {
		this.id = (long) id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
