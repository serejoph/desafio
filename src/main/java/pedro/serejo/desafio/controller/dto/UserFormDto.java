package pedro.serejo.desafio.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import pedro.serejo.desafio.model.User;
import pedro.serejo.desafio.validation.EmailConstraint;

public class UserFormDto {

	@Size(min = 5, message = "O nome do usuário deve ter pelo menos 5 letras")
	private String name;
	@EmailConstraint
	@Email
	@NotBlank(message = "Insira o e-mail do usuário")
	private String email;
	@Size(min = 6, message ="A senha deve conter no mínimo 6 caracteres")
	private String password;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserFormDto() {
		
	}
	
public UserFormDto(User user) {
	this.name = user.getName();
	this.email = user.getEmail();
	
	}
	
	
	
}
