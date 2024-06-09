package edu.uclm.esi.sqa.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Hamiltoniano {
	
	private List<Ecuacion> ecuaciones = new ArrayList<>();
	private Suma suma = new Suma();
	
	public void add(Ecuacion equation) {
		this.ecuaciones.add(equation);
	}
	
	public Suma getSuma() {
		return suma;
	}
	
	public void generarSuma() {
		for (Ecuacion ecuacion : ecuaciones) {
			if(!ecuacion.tieneIndependiente()) {
				//generación normal de la suma (multiplicar y hacer cuadrados de cada uno de los sumandos)
				for (Simple s : ecuacion.getSumandos()) {
					Cuadrado c = new Cuadrado();
					c.setFactor(s.getFactor()*ecuacion.getLambda());
					c.setIndex(s.getIndex());
					suma.add(c);
				}
			}
			else {
				//generación de cuadrados de cada componente; generación del primero por todos los demás, segundo todo menos
				List<Simple> sumandos = ecuacion.getSumandos();
				Simple independiente = sumandos.get(sumandos.size()-1);
				for (int i = sumandos.size() - 1; i >= 0; i--) {
				    Simple s = sumandos.get(i);
				    if (s.esIndependiente());
				        //s.setFactor(s.getFactor()*s.getFactor());
				    else {
				        Cuadrado c = new Cuadrado();
				        Cuadrado c2 = new Cuadrado();
				        c.setFactor((s.getFactor() * s.getFactor()) * ecuacion.getLambda());
				        c.setIndex(s.getIndex());
				        suma.add(c);
				        c2.setFactor((s.getFactor()*independiente.getFactor())* 2 * ecuacion.getLambda());
				        c2.setIndex(s.getIndex());
				        suma.add(c2);
				    }
				}

				for (int i = 0; i < ecuacion.getSumandos().size(); i++) {
					for (int j = i + 1; j < ecuacion.getSumandos().size()-1; j++) {
						Doble d = new Doble();
						d.setFactor((ecuacion.getSumandos().get(i).getFactor()* ecuacion.getSumandos().get(j).getFactor() * ecuacion.getLambda())*2);
						d.setIndex(ecuacion.getSumandos().get(i).getIndex());
						d.setIndexB(ecuacion.getSumandos().get(j).getIndex());
						suma.add(d);
					}
				}
			}	
		}
		//Juntar por índice y tipo
		for (int i = 0; i < suma.getSumandos().size(); i++) {
			Sumando s = suma.getSumandos().get(i);
			if(!(s.getTipo().equals("class edu.uclm.esi.sqa.model.Doble")))
				for (int j = i + 1; j < suma.getSumandos().size(); j++) { 
					Sumando s2 = suma.getSumandos().get(j);
					if (s.getIndex() == s2.getIndex() && s.getTipo().equals(s2.getTipo()) && s != s2) {
						s.setFactor(s.getFactor() + s2.getFactor());
						suma.remove(s2);
						j--;
				}
			}
		}
	}

}
