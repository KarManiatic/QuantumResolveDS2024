package edu.uclm.esi.sqa.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.sqa.services.CommonService;
import edu.uclm.esi.sqa.model.Hamiltoniano;
import edu.uclm.esi.sqa.model.MatrizTriangular;

public abstract class CommonController {
	
	@Autowired
	private CommonService commonService;
		
	protected boolean validarToken(String token) throws IOException{
		boolean valido = this.commonService.validarToken(token);
		if(valido) {
			this.crearCarpeta(token);
		}
		return valido;
	}
	
	private void crearCarpeta(String token) {
		new File(this.getName(token)).mkdir();
	}
	
	public String getName(String token) {
		String userPath = System.getProperty("user.home");
		if(!userPath.endsWith(File.separator))
			userPath = userPath+ File.separator;
		userPath = userPath + "practicaDisoft" + File.separator;
		return userPath + File.separator;	
	}
	
	public String saveHamiltoniano(String token, Hamiltoniano h) throws FileNotFoundException, IOException {
	    String fileName = this.getName(token) + "hamiltoniano.txt";
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(h);
        }
	    return fileName; // Devuelve el nombre del archivo guardado
	}
	public String saveMatrizTriangular(String token, MatrizTriangular mt) throws FileNotFoundException, IOException{
		String fileName = this.getName(token) + "matriztriangular.txt";
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
	    	oos.writeObject(mt);
	    }
	    return fileName;
	}
	
	public String saveCodigo(String token, String codigo) throws FileNotFoundException, IOException {
		String fileName = this.getName(token) + "codigo.py";
		try(FileOutputStream fos = new FileOutputStream(fileName)){
            fos.write(codigo.getBytes());
            }
		return fileName;
	}
	
	public String validarPeticion(HttpServletRequest req) {
		String token = req.getHeader("token");
		if(token == null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ande vas");
		
		try {
			if(!validarToken(token))
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tu token no sirve");
		}
		catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "El sistema de control de credenciales...");
		}
		return token;
	}

}
