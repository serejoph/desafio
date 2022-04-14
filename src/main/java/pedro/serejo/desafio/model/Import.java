package pedro.serejo.desafio.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Import {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	LocalDateTime importDateTime;
	LocalDate transactionsDate;
	
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
	
	public Import (LocalDate transactionsDate) {
		this.transactionsDate = transactionsDate;
		this.importDateTime = LocalDateTime.now();
	}
}
