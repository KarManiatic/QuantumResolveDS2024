package edu.uclm.esi.sqausers.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;

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

import edu.uclm.esi.sqausers.model.CredencialesRegistro;
import edu.uclm.esi.sqausers.model.User;
import edu.uclm.esi.sqausers.services.UserService;

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
		User user = new User();
		user.setEmail(cr.getEmail());
		user.setPwd(cr.getPwd1());
		
		this.userService.registrar(user);
	}
	
	@PutMapping("/login")
	public Map<String, String> login(HttpSession session, @RequestBody User user) {
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