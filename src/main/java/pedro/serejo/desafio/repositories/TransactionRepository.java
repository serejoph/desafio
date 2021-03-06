package pedro.serejo.desafio.repositories;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pedro.serejo.desafio.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	public List<Transaction> findByLocalDate(LocalDate localDate);
	
	public List<Transaction> findByUploadId(Long id);
	
	public List<Transaction> findByYearMonth(YearMonth yearMonth);
	
}
