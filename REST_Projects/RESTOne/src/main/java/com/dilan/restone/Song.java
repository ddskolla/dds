package com.dilan.restone;

public class Song {
	
	String name;
	String singer;
 
	public String getTitle() {
		return name;
	}
 
	public void setTitle(String title) {
		this.name = title;
	}
 
	public String getSinger() {
		return singer;
	}
 
	public void setSinger(String singer) {
		this.singer = singer;
	}
 
	@Override
	public String toString() {
		return "Song [name=" + name + ", singer=" + singer + "]";
	}

}
