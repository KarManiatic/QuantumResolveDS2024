package edu.uclm.esi.sqa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Suma implements Serializable {
	private List<Sumando> sumandos;
	
	public Suma() {
		this.sumandos = new ArrayList<>();
	}
	
	public List<Sumando> getSumandos(){
		return sumandos;
	}
	
	public void add(Sumando...sumandos) {
		for(Sumando s : sumandos)
			this.sumandos.add(s);
	}
	
}
