package pedro.serejo.desafio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	AuthenticationManager authManager;

	@GetMapping
	public String loginForm(String error) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken))
			return "redirect:/upload/form";
		
		
		
		
		return "Login";
	}

//	@PostMapping
//	public String login(UserLoginDto userLoginDto) {
//
//		UsernamePasswordAuthenticationToken authToken = userLoginDto.toToken();
//		Authentication authenticate = authManager.authenticate(authToken);
//
//		SecurityContextHolder.getContext().setAuthentication(authenticate);
//		return "redirect:user/list";
//
//	}

}
