package com.android.entity;

import org.json.JSONObject;

public class User {

	private String id;
	private String username;
	private String imageUrl;

	public User(JSONObject obj) {
		try {
			id = obj.getString("id");
			username = obj.getString("username");
			imageUrl = obj.getString("imgurl");
		} catch (Exception e) {

		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	

}
