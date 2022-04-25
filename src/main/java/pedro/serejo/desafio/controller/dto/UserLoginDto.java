package pedro.serejo.desafio.controller.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserLoginDto {

	private String email;
	private String password;
	
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
	@Override
	public String toString() {
		return "UserLoginDto [email=" + email + ", password=" + password + "]";
	}
	public UsernamePasswordAuthenticationToken toToken() {
		var token = new UsernamePasswordAuthenticationToken(email, password);
		return token;
	}
	
	
	
}
