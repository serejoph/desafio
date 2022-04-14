package pedro.serejo.desafio.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import pedro.serejo.desafio.model.Transaction;

@Component
public class TransactionRepositoryImpl {


	@PersistenceContext
	EntityManager em;



	public List<Transaction> findByLocalDate(LocalDate localDate) {
		
		LocalDateTime start = localDate.atStartOfDay();
		LocalDateTime end = localDate.plusDays(1).atStartOfDay();

		var query = em
				.createQuery("SELECT t from Transaction t where t.dateTime >= ?1 AND t.dateTime < ?2", Transaction.class)
				.setParameter(1, start)
				.setParameter(2, end);
		return query.getResultList();
		
	}

}
