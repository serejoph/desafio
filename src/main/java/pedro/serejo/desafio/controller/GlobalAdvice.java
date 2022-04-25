package pedro.serejo.desafio.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import pedro.serejo.desafio.model.User;

@ControllerAdvice (assignableTypes = {UserController.class, TransactionsController.class, LoginController.class})
public class GlobalAdvice {

	@ModelAttribute("user")
	public User getUser() {
try {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (User) auth.getPrincipal();
} catch (ClassCastException e) {
	return new User();
}
		
		
		
	}
	
	@ModelAttribute("token")
	public String token(CsrfToken token) {

		return token.getToken();		
	}
	

}
