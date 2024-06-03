package edu.uclm.esi.sqaUsers.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;


@Entity
@Table(
	name = "usuarios",	
	indexes = {
		@Index(columnList = "name", unique = true),
		@Index(columnList = "email", unique = true)
		}
	)
public class Usuario {
	@Id @Column(length = 36)
	private String id;
	@Column(length = 100, nullable = false)
	private String name;
	@Column(length = 100, nullable = false)
	private String email;
	@Column(length = 100, nullable = false)
	private String pwd;
	
	@Transient
	private Token token;
	
	public Usuario() {
		this.id = UUID.randomUUID().toString();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
}