package com.dilan.restone;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "Book")
public class Book {
 
	String name;
	String author;
	int ISBN;
 
	@XmlElement
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement
	public String getAuthor() {
		return author;
	}
 
	public void setAuthor(String author) {
		this.author = author;
	}
 
	@XmlAttribute
	public int getISBN() {
		return ISBN;
	}
 
	public void setISBN(int ISBN) {
		this.ISBN = ISBN;
	}
 
}