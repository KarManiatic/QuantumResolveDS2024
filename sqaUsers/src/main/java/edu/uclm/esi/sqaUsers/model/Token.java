package edu.uclm.esi.sqaUsers.model;

public class Token {
	
	private final static int DURACION = 15000;
	private String id;
	private Usuario user;
	private long horaFin;

	public Token(String id, Usuario user) {
		this.id = id;
		this.user = user;
		this.horaFin = System.currentTimeMillis() + DURACION;
	}
	
	public String getId() {
		return id;
	}

	public boolean caducado() {
		return System.currentTimeMillis() > this.horaFin;
	}

	public void incrementarTiempo() {
		this.horaFin = System.currentTimeMillis() + DURACION;
	}
}