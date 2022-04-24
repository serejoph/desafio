package pedro.serejo.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import pedro.serejo.desafio.model.User;

@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;
	
	
	public void sendPassword(User user, String password) {
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(user.getEmail());
		msg.setSubject("Conta criada com sucesso");
		msg.setText("Sua senha é: " + password);
		mailSender.send(msg);
		
		
		
	}

}
