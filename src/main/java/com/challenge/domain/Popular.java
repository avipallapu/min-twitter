package com.challenge.domain;

public class Popular {
	private int id;
	private int follower_id;
	
	public Popular(int int1, int int2) {
		this.id = int1;
		this.follower_id = int2;
	}
	
	public Popular(int int1) {
		this.id = int1;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFollower_id() {
		return follower_id;
	}
	public void setFollower_id(int follower_id) {
		this.follower_id = follower_id;
	}
	
	
}
