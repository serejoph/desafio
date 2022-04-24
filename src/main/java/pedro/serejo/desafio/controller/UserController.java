package pedro.serejo.desafio.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.bytebuddy.implementation.bind.MethodDelegationBinder.BindingResolver;
import pedro.serejo.desafio.controller.dto.UserFormDto;
import pedro.serejo.desafio.model.User;
import pedro.serejo.desafio.repositories.UserRepository;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("new-user")
	public String userForm(CsrfToken token, Model model) {

		UserFormDto user = new UserFormDto();
		model.addAttribute(user);
		model.addAttribute("token", token.getToken());
		return "NewUserForm";
	}

	@PostMapping("/new-user")
	public String newUser(@Valid UserFormDto userForm, BindingResult res, CsrfToken csrfToken, Model model) {
		
		if (res.hasErrors()){
			model.addAttribute("token", csrfToken.getToken());
			return "NewUserForm";
			
		}
		User user = new User(userForm);
		userRepository.save(user);
		
		return "redirect:/user/list";
		
	}

	@GetMapping("list")
	public String listUsers(Model model, CsrfToken token) {
		model.addAttribute("token", token.getToken());
		model.addAttribute("users", userRepository.findAll());
		return "UserList";
	}


	@PostMapping("delete")
	public String deleteUser(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent() && !email.equals("admin@email.com.br"))
			userRepository.delete(user.get());

		return "redirect:/user/list";
	}

}
