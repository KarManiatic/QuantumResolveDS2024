package edu.uclm.esi.sqa.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

@Service
public class DWService {
	
	/*
	public String generarCodigo(int[][] matriz) throws Exception {
		String template = this.read("dwaveTemplate.txt");
		
		StringBuilder sb = new StringBuilder("x=[");
		for(int i=0; i<matriz.length; i++) {
			sb.append("\"x" + i + "\",");
		}
		sb.append("]\n");
		String lineaX = sb.toString();
		
		int posRules = template.indexOf("#RULES#")+"#RULES#".length()+1;
		String inicio = template.substring(0, posRules);
		
		String fin = template.substring(posRules);
		
		String inicializacionMatriz = "...";
		String code = inicio + lineaX + inicializacionMatriz + fin;
		return code;
	}*/
	
	public String generarCodigo(int[][] matriz) throws Exception {
	    String template = this.read("dwaveTemplate.txt");
	    
	    StringBuilder sb = new StringBuilder("x=[");
	    for(int i=0; i<matriz.length; i++) {
	        sb.append("\"x" + i + "\",");
	    }
	    sb.append("]\n");
	    String lineaX = sb.toString();
	    
	    int posRules = template.indexOf("#RULES#")+"#RULES#".length()+1;
	    String inicio = template.substring(0, posRules);
	    
	    String fin = template.substring(posRules);
	    
	    // Inicialización de la matriz
	    StringBuilder sbMatriz = new StringBuilder("Q = {");
	    for(int i=0; i<matriz.length; i++) {
	        for(int j=i; j<matriz[i].length; j++) {
	            if(matriz[i][j] != 0) {
	                sbMatriz.append("(\"x" + i + "\", \"x" + j + "\"): " + matriz[i][j] + ",");
	            }
	        }
	    }
	    sbMatriz.append("}\n");
	    String inicializacionMatriz = sbMatriz.toString();
	    
	    String code = inicio + lineaX + inicializacionMatriz + fin;
	    return code;
	}

	

	public String read(String fileName) throws FileNotFoundException, IOException {
		ClassLoader classLoader = getClass().getClassLoader();

		try (InputStream fis = classLoader.getResourceAsStream(fileName)) {
			byte[] b = new byte[fis.available()];
			fis.read(b);
			String s = new String(b);
			return s;
		}
	}
}
