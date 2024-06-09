package edu.uclm.esi.sqa.http;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.sqa.services.EcuacionesService;
import edu.uclm.esi.sqa.model.Hamiltoniano;
import edu.uclm.esi.sqa.model.MatrizTriangular;

@CrossOrigin
@RestController
@RequestMapping("ecuaciones")
public class EcuacionesController extends CommonController{
	
	@Autowired
	private EcuacionesService service;

	
	@PutMapping("/generarHamiltoniano")
	public Hamiltoniano generarHamiltoniano (HttpServletRequest req, @RequestBody List<Map<String, Object>> ecuaciones) {
		super.validarPeticion(req);
		
		
		String token = req.getHeader("token");
		if(token==null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Intento de acceso no autorizado");
		
		try {
			if (!validarToken(token))
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token inválido");
		}
		catch (IOException E) {
			throw new  ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "El sistema de control de credenciales no está funcionando." );
		}
		
		Hamiltoniano h = EcuacionesService.generarHamiltoniano(ecuaciones);
		try {
			super.saveHamiltoniano(token, h);
		}
		catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error de disco: " + e.getMessage());
		}
		return h;
	}
	
	@PutMapping("/generarMatriz")
	public int[][] generarMatriz(HttpServletRequest req, @RequestBody String hFileName) {
        super.validarPeticion(req);
        
        String token = req.getHeader("token");
        if(token==null)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Intento de acceso no autorizado");
        
        try {
            if (!validarToken(token))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token inválido");
        }
        catch (IOException E) {
            throw new  ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "El sistema de control de credenciales no está funcionando." );
        }
        
        
        try {
        	MatrizTriangular mt = EcuacionesService.generarMatrizTriangular(hFileName);
            super.saveMatrizTriangular(token, mt);
            return mt.GetMatriz();
        }
        catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error de disco: " + e.getMessage());
        }
        
        
    }	
}
