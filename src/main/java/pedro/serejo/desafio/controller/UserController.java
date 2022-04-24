package pedro.serejo.desafio.controller;

import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pedro.serejo.desafio.controller.dto.UserFormDto;
import pedro.serejo.desafio.model.User;
import pedro.serejo.desafio.repositories.UserRepository;
import pedro.serejo.desafio.service.MailService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	MailService mailService;

	@GetMapping("new-user")
	public String userForm(CsrfToken token, Model model) {

		UserFormDto user = new UserFormDto();
		model.addAttribute(user);
		return "NewUserForm";
	}

	@PostMapping("/new-user")
	public String newUser(@Valid UserFormDto userForm, BindingResult res, CsrfToken csrfToken, Model model) {

		if (res.hasErrors()) {
			return "NewUserForm";

		}
		User user = new User(userForm);
		Random r = new Random();
		String rawPassword = String.valueOf(r.nextInt(100_000, 999_999));
		user.setPassword(rawPassword);
		userRepository.save(user);

		new Thread(() -> mailService.sendPassword(user, rawPassword)).start();

		return "redirect:/user/list";

	}

	@GetMapping("list")
	public String listUsers(Model model, CsrfToken token) {
		
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

	@GetMapping("manage")
	public String manageUsers() {
		return "UserList";
	}

	@GetMapping("update/{id}")
	public String updateForm(@PathVariable Long id, Model model) {
		Optional<User> userOptional = userRepository.findById(id);

		User user = userOptional.get();
		model.addAttribute("id", user.getId());
		model.addAttribute("updatingUser", new UserFormDto(user));
		return "UpdateUser";

	}
	
	@Transactional
	@PostMapping("update")
	public String updateUser(UserFormDto userFormDto, Long id, Model model) {
		
		User user = userRepository.findById(id).get();
		Optional<User> other = userRepository.findByEmail(userFormDto.getEmail());
		if (other.isPresent() && !other.get().equals(user)) {

			model.addAttribute("id", id);
			model.addAttribute("updatingUser", userFormDto);
			model.addAttribute("emailError", "E-mail já cadastrado!");
			return "UpdateUser";
		}
		user.setEmail(userFormDto.getEmail());
		user.setName(userFormDto.getName());
		userRepository.save(user);
		
		return "redirect:/user/list";
	}

}
