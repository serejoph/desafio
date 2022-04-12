package pedro.serejo.desafio.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pedro.serejo.desafio.model.Transaction;
import pedro.serejo.desafio.repositories.TransactionRepository;

@Component
public class TransactionDao {

	@Autowired
	TransactionRepository repo;

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
