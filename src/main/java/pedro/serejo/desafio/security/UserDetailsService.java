package pedro.serejo.desafio.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pedro.serejo.desafio.model.User;
import pedro.serejo.desafio.repositories.UserRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByEmail(username);
		
		if(user.isPresent()) return user.get();
		
		throw new UsernameNotFoundException("Username Not Found");
		
	}
	
}
