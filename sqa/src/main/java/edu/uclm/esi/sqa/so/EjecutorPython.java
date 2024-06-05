package edu.uclm.esi.sqa.so;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EjecutorPython {
	
	public String ejecuta(String fileName) {
		// 1) "Abrir" la consola en el directorio en el que queremos ejecutar el proceso 
		// 2) Establecer el PATH y otras variables de entorno
		// 3) Construimos el comando que queremos ejecutar
		// 4) Redireccionar la salida estándar de la consola a un archivo
		// 5) Redireccionar la salida de error de la consola a un archivo
		// 6) Lanzar el proceso 
		// 7) Esperamos a que termine
		// 8) Leer el archivo de salido y el archivo de error
		// 9) Si no hay error, devolver a quien corresponda el resultado de la salida 
		// 10) Si hay error, devolver a quien corresponda el mensaje de error para que haga llegar al front una expcepcion
		
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
				//Paso 10
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
