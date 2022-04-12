package pedro.serejo.desafio.validation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pedro.serejo.desafio.dao.TransactionDao;
import pedro.serejo.desafio.exceptions.TransactionsAlreadySavedException;
import pedro.serejo.desafio.model.Transaction;
import pedro.serejo.desafio.repositories.TransactionRepository;

@Component
public class CsvValidator {

	@Autowired
	TransactionDao dao;
	
	@Autowired
	TransactionRepository rep;

	 
	public List<Transaction> validate(String[] csvLines) {	
		
		List<Transaction> validTransactions = new ArrayList<>(); 
		try {
			String[] firstTransaction = csvLines[0].split(",");
			String firstDateString = firstTransaction[7];
			LocalDate firstLocalDate = LocalDateTime.parse(firstDateString).toLocalDate();
			
			if (dao.findByLocalDate(firstLocalDate).size()>0) throw new TransactionsAlreadySavedException(firstLocalDate.toString());
			
			lines:
			for (String line : csvLines) {
				
				try {
				String[] fields = line.split(",");
				for(String field : fields) {
					if (field.isBlank()) continue lines;
				}
				String dateTimeField = fields[7];		
				LocalDateTime localDateTime = LocalDateTime.parse(dateTimeField);
				LocalDate localDate = localDateTime.toLocalDate();
				
				
				
				if (localDate.equals(firstLocalDate)) {
					
					Transaction t = new Transaction(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], new BigDecimal(fields[6]),localDateTime);
					
					validTransactions.add(t);
				}
				}catch (IndexOutOfBoundsException e) {
					continue;
				}
			}
			
		} catch (DateTimeParseException | IndexOutOfBoundsException e) {
			return validTransactions;
		}

		return validTransactions;
	}
	


}
