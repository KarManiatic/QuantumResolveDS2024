package edu.uclm.esi.sqa.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.uclm.esi.sqa.model.Ecuacion;
import edu.uclm.esi.sqa.model.Hamiltoniano;
import edu.uclm.esi.sqa.model.MatrizTriangular;

@Service
public class EcuacionesService {
	
	public EcuacionesService() {
		System.out.println("CREADO");
	}
	
	public String contarIncognitas(String eq) {
		int cont = 0;
		for(int i = 0; i<eq.length(); i++) 
			if(eq.charAt(i)=='x') 
				cont++;

		return "Hay " + cont + " incógnitas";
	}
	
	public int calcular(String eq, List<Integer> x) {
		//Si me llega 4x0+3x1 y lista 3 y 5. Crear una clase ecuación, que esté compuesta por clases sumandos, creados con factor e index.
		//Para hacerlo funcionar, habrá que crear un objeto ecuación con dos sumandos, cada uno con un factor diferente
		Ecuacion ecuacion = new Ecuacion (eq);
		return ecuacion.calcular(null);
	}
	
	public static Hamiltoniano generarHamiltoniano(List<Map<String, Object>> ecuaciones) {
		Hamiltoniano h = new Hamiltoniano();
		for(Map<String, Object> ecuacion : ecuaciones) {
			String eq = (String) ecuacion.get("eq");
			int lambda = (int)ecuacion.get("lambda");
			Ecuacion equation = new Ecuacion(eq);
			equation.setLambda(lambda);
			h.add(equation);
		}
		return h;
	}
	
	public static MatrizTriangular generarMatrizTriangular(String hFileName) {
		// Lógica para obtener los datos relevantes del archivo hFileName
	    List<Integer> datos = obtenerDatosDesdeArchivo(hFileName);

	    // Creamos la matriz triangular utilizando los datos obtenidos
	    MatrizTriangular matrizTriangular = MatrizTriangular.generarMatrizTriangular(datos);

	    return matrizTriangular;
	}

	private static List<Integer> obtenerDatosDesdeArchivo(String hFileName) {
		// TODO Auto-generated method stub
		return null;
	}
		
	
}

