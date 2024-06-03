package edu.uclm.esi.sqaUsers.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import edu.uclm.esi.sqaUsers.model.Usuario;

@Repository
public interface UserDAO extends JpaRepository<Usuario, String> {

	Optional<Usuario> findByEmailAndPwd(String email, String pwd);
	
}
