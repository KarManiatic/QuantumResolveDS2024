package edu.uclm.esi.sqa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PruebaSerial {
	 
	public static void main(String[] args) throws IOException {
		Persona pepe = new Persona("Pepe", "Perez");
		Persona ana = new Persona("Ana", "López");
		
		ArrayList<Persona> personas = new ArrayList<>();
		personas.add(pepe);
		personas.add(ana);
		
		FileOutputStream out = new FileOutputStream("ruta al archivo/pepe.obj");
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(pepe);
		out.close();
		
		out = new FileOutputStream("ruta al archivo/ana.obj");
		oos = new ObjectOutputStream(out);
		oos.writeObject(ana);
		out.close();
		
		out = new FileOutputStream("ruta al archivo/personas.obj");
		oos = new ObjectOutputStream(out);
		oos.writeObject(pepe);
		out.close();
	}
	
	
}

class Persona {
	
	private String n;
	private String a;
	
	public Persona(String n, String a) {
		this.n = n;
		this.a = a;
	}
}