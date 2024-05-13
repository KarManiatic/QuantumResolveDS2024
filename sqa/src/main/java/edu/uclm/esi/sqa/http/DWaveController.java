package edu.uclm.esi.sqa.http;

import java.io.IOException;
import java.util.HashMap;
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

import edu.uclm.esi.sqa.model.Hamiltoniano;
import edu.uclm.esi.sqa.model.MatrizTriangular;
import edu.uclm.esi.sqa.services.*;
import edu.uclm.esi.sqa.so.EjecutorPython;

@RestController
@RequestMapping("dwave")
@CrossOrigin("*")
public class DWaveController extends CommonController{
	
	@Autowired
	private DWService service;
	
	@PutMapping("/generarCodigo")
    public String generarCodigo(HttpServletRequest req, @RequestParam int[][] matriz) {
		String token = super.validarPeticion(req);
		
		try {
			String codigo = this.service.generarCodigo(matriz);
			super.saveCodigo(token, codigo);
			return codigo;
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error" + e.getLocalizedMessage());
		}
	}

	
	@PutMapping("/ejecutarCodigo")
	public Map<String,Object> ejecutarCodigo(HttpServletRequest req, @RequestBody List<Map<String, Object>> ecuaciones) { //Falta la segunda parte del request body
		String token = super.validarPeticion(req);
		
		try {
			Hamiltoniano h = EcuacionesService.generarHamiltoniano(ecuaciones);
			String hFileName = super.saveHamiltoniano(token, h);
			MatrizTriangular mt = EcuacionesService.generarMatrizTriangular(hFileName);
			String mtFileName = super.saveMatrizTriangular(token, mt);
			String codigo = this.service.generarCodigo(mt.GetMatriz());
			String codigoFileName = super.saveCodigo(token, codigo);
			
			EjecutorPython ejecutor = new EjecutorPython();
			Map<String, Object> resultado = new HashMap<>();
			String key;
			resultado.put("codigo", ejecutor.ejecuta(codigoFileName));
			return resultado;		
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: " + e.getMessage());
		}
		
	} 

}