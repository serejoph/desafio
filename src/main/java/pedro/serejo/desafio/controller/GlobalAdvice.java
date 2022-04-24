package pedro.serejo.desafio.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import pedro.serejo.desafio.model.User;

@ControllerAdvice
public class GlobalAdvice {

	@ModelAttribute("user")
	public User getUser() {

		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	@ModelAttribute("token")
	public String token(CsrfToken token) {

		return token.getToken();		
	}
	

}
