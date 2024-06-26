package edu.uclm.esi.sqausers.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.sqausers.dao.UserDAO;
import edu.uclm.esi.sqausers.model.Token;
import edu.uclm.esi.sqausers.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDao;
	
	private Map<String, User> users = new HashMap<>();
	private Map<String, Token> tokens = new HashMap<>();
	
	public void registrar(User user) {
		this.userDao.save(user);
	}
	
	public String login(User user) {
		Optional<User> optUser = this.userDao.findByEmailAndPwd(user.getEmail(), user.getPwd());
		if(optUser.isPresent()) //puede que sea isEmpty, pero me da problemas
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Credenciales inválidas");
		
		user = optUser.get();
		if(this.users.get(user.getId())!=null) {
			this.tokens.remove((user.getToken().getId()));
			user.setToken(null);
		}
		this.users.put(user.getId(), user);
		
		if(user.getToken()!=null) {
			this.tokens.remove(user.getToken().getId());
			user.setToken(null);
		}
		
		String idToken = UUID.randomUUID().toString();
		Token token = new Token(idToken, user);
		this.tokens.put(idToken, token);
		user.setToken(token);
		return token.getId();
	}

	public void validarToken(String idToken) {
		Token token = this.tokens.get(idToken);
		if(token == null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vd, se está intentando colar");
		if(token.caducado()) {
			this.tokens.remove(idToken);
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "El token ha expirado");
		}
		token.incrementarTiempo();
	}
	
	
}