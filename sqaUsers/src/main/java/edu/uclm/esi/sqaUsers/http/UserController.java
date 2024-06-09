package edu.uclm.esi.sqaUsers.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.sqaUsers.model.CredencialesRegistro;
import edu.uclm.esi.sqaUsers.model.Usuario;
import edu.uclm.esi.sqaUsers.services.UserService;
import jakarta.servlet.http.HttpSession;

//Controlador REST para manejar peticiones relacionadas con ecuaciones
@RestController
@RequestMapping("users")
@CrossOrigin("*")//Permite el acceso a cualquier origen
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/registrar")
	public void registrar(@RequestBody CredencialesRegistro cr) {
		cr.comprobar();
		Usuario usuario = new Usuario();
		usuario.setEmail(cr.getEmail());
		usuario.setPwd(cr.getPwd1());
		usuario.setName(cr.getName());
		
		this.userService.registrar(usuario);
	}
	
	@PutMapping("/login")
	public Map<String, String> login(HttpSession session, @RequestBody Usuario user) {
		Map<String, String> result = new HashMap<>();
		result.put("token", this.userService.login(user));
		return result;
	}
	
	@GetMapping("/validarToken")
	public void validarToken(@RequestParam String token) {
		this.userService.validarToken(token);
	}
	
	@GetMapping("/validarToken2")
	public void validarToken2(@PathVariable String token) {
		this.userService.validarToken(token);
	}
	
	
}