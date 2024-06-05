package edu.uclm.esi.sqa.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

@Service
public class DWService {
	
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
	    
	    StringBuilder sbRules = new StringBuilder();
	    for(int i=0; i<matriz.length; i++) {
	        for(int j=0; j<matriz[i].length; j++) {
	            if(matriz[i][j] != 0) {
	                sbRules.append("Q[('x" + i + "', 'x" + j + "')] = " + matriz[i][j] + "\n");
	            }
	        }
	    }
	    String reglas = sbRules.toString();
	    
	    String code = inicio + lineaX + reglas + fin;
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
