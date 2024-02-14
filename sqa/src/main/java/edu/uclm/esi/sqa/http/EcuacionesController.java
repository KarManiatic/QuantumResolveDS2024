package edu.uclm.esi.sqa.http;

import java.util.List;
import java.util.Map;

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
public class EcuacionesController {
	
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
	public Hamiltoniano generarHamiltoniano (@RequestBody List<Map<String, Object>> ecuaciones) {
		return this.service.generarHamiltoniano(ecuaciones);
	}
}
