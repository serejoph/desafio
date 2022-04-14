package pedro.serejo.desafio.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pedro.serejo.desafio.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	public List<Transaction> findByLocalDate(LocalDate localDate);
	
}
