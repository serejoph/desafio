package pedro.serejo.desafio.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import pedro.serejo.desafio.repositories.UserRepository;

public class EmailValidation implements ConstraintValidator<EmailConstraint, String>{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (userRepository.findByEmail(value).isPresent()) return false;
		return true;
	}

}
