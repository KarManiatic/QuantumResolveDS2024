package edu.uclm.esi.sqa.http;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.sqa.model.Hamiltoniano;
import edu.uclm.esi.sqa.services.EcuacionesService;

@RestController
@RequestMapping("ecuaciones")
public class EcuacionesController extends CommonController{
	
	@Autowired
	private EcuacionesService service;
	
	@GetMapping("/recibir")
	public String recibir(@RequestParam String eq) {
		if(eq.trim().length() == 0)
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "La ecuación no puede estar vacía");
		
		return this.service.contarIncognitas(eq);
	}
	
	@PutMapping("/recibir")
	public String recibirPorPut(@RequestBody Map<String, Object> info) {
		String eq = info.get("eq").toString();
		int lambda = (int)info.get("lambda");
		
		return this.recibir(eq);
	}
	
	@PutMapping("/calcular")
	//Recibe el JSON "eq":"4x0+3x1", "lambda":7, "x":[3,5] como lista de enteros el último
	public int calcular(@RequestBody Map<String, Object> info){
		String eq = info.get("eq").toString();
		int lambda = (int)info.get("lambda");
		List<Integer> x = (List<Integer>) info.get("x");
		
		return this.service.calcular(eq, x);
	}
	
	@PutMapping("/generarHamiltoniano")
	public Hamiltoniano generarHamiltoniano (HttpServletRequest req, @RequestBody List<Map<String, Object>> ecuaciones) {
		super.validarPeticion(req);
		
		
		String token = req.getHeader("token");
		if(token==null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ande vas");
		
		try {
			if (!validarToken(token))
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Tu token no sirve");
		}
		catch (IOException E) {
			throw new  ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "El sistema de control de credenciales no está funcionando." );
		}
		
		Hamiltoniano h = this.service.generarHamiltoniano(ecuaciones);
		try {
			super.saveHamiltoniano(token, h);
		}
		catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error de disco: " + e.getMessage());
		}
		return h;
	}
	
	
}
