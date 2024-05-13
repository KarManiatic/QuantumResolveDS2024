package edu.uclm.esi.sqausers.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(indexes = {
	@Index(columnList = "name", unique = true),
	@Index(columnList = "email", unique = true)
})

public class User {
	@Id @Column(length = 36)
	private String id;
	@Column(length = 100, nullable = false)
	private String email;
	@Column(length = 100, nullable = false)
	private String pwd;
	
	@Transient
	private Token token;
	
	public User() {
		this.id = UUID.randomUUID().toString();
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPwd() {
		return pwd;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public void setToken(Token token) {
		this.token = token;	
	}
	
	public Token getToken() {
		return this.token;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	
}