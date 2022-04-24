package pedro.serejo.desafio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pedro.serejo.desafio.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByEmail(String email);
	
}
