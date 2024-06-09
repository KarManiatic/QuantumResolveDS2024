package edu.uclm.esi.sqa.so;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EjecutorPython {
	
	public String ejecuta(String fileName) {
		
		fileName = "C:\\Users\\Carlos\\practicaDisoft\\codigo.py";
		ProcessBuilder pb = new ProcessBuilder("python", fileName);
		
		Map<String, String> env = pb.environment();
		env.put("PATH", env.get("PATH") + File.pathSeparator + "C:\\Python312\\");
		
		String carpeta = fileName.substring(0, fileName.length()-9);
		String salida = carpeta + "salida.txt";
		String errores = carpeta + "errores.txt";
		
		pb.directory(new File(carpeta));
		pb.redirectOutput(new File(salida));
		pb.redirectError(new File(errores));
		
		try {
			Process process = pb.start();
			process.waitFor();
			if(new File(errores).length()>0) {
			}
			else {
				String respuesta;
				try (FileInputStream fis = new FileInputStream(new File(salida))) {
					byte[] bytes = new byte[fis.available()];
					fis.read(bytes);
					respuesta = new String(bytes);
					return respuesta;
				}
			}
		}
		catch (IOException | InterruptedException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Pasa algo");
		}
		return errores;
	}
}
