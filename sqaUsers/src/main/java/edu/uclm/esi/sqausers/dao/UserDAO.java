package edu.uclm.esi.sqausers.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import edu.uclm.esi.sqausers.model.User;

public interface UserDAO extends JpaRepository<User, String> {

	Optional<User> findByEmailAndPwd(String email, String pwd);
	
}
