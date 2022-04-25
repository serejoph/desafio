package pedro.serejo.desafio.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Import {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	LocalDateTime importDateTime;
	LocalDate transactionsDate;
	
	@ManyToOne
	User user;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getImportDateTime() {
		return importDateTime;
	}
	public void setImportDateTime(LocalDateTime importDateTime) {
		this.importDateTime = importDateTime;
	}
	public LocalDate getTransactionsDate() {
		return transactionsDate;
	}
	public void setTransactionsDate(LocalDate transactionsDate) {
		this.transactionsDate = transactionsDate;
	}
	
	public Import() {
		
	}
	
	public Import (LocalDate transactionsDate, User user) {
		this.transactionsDate = transactionsDate;
		this.importDateTime = LocalDateTime.now();
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
