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
	
}
