package pedro.serejo.desafio.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pedro.serejo.desafio.model.Import;

public interface ImportRepository extends JpaRepository<Import, Long>{

	public Optional<Import> findByTransactionsDate(LocalDate localDate);
	
}
