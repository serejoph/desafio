package pedro.serejo.desafio.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import pedro.serejo.desafio.model.User;
import pedro.serejo.desafio.repositories.UserRepository;

public class EmailValidation implements ConstraintValidator<EmailConstraint, String>{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Optional<User> userOptional = userRepository.findByEmail(value);
		if (userOptional.isPresent() && userOptional.get().isEnabled()) {			
			return false;
		}
		return true;
	}

}
