package pedro.serejo.desafio.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import pedro.serejo.desafio.exceptions.UploadException;
import pedro.serejo.desafio.model.Import;
import pedro.serejo.desafio.model.Transaction;
import pedro.serejo.desafio.model.User;
import pedro.serejo.desafio.repositories.ImportRepository;
import pedro.serejo.desafio.repositories.TransactionRepository;

@Service
public class TransactionsValidator {

	@Autowired
	ImportRepository iRepository;
	@Autowired
	TransactionRepository tRepository;

	public List<Transaction> validate(List<Transaction> transactions) {
		
		if (transactions == null || transactions.isEmpty()) throw new UploadException("O arquivo inválido");
		
		LocalDate firstDate = transactions.get(0).getDateTime().toLocalDate();
		checkImports(firstDate);
		
		List<Transaction> validTransactions = transactions.stream().filter(this::checkEmptyFields)
				.filter(x -> x.getDateTime().toLocalDate().equals(firstDate)).collect(Collectors.toList());
		if (validTransactions.isEmpty()) throw new UploadException("O arquivo não contém transações válidas");
		
		
		
		return validTransactions;
	}

	private void checkImports(LocalDate firstDate) {
		Optional<Import> imp = iRepository.findByTransactionsDate(firstDate);
		if(imp.isPresent()) throw new UploadException("A importação do dia já aconteceu");
	}
	
	public boolean checkEmptyFields(Transaction transaction) {
		
		Object[] fields = {transaction.getAmmount(), transaction.getDateTime(),
				transaction.getRecipient().getAccount(), transaction.getRecipient().getBankName(), transaction.getRecipient().getBranch(),
				transaction.getSender().getAccount(), transaction.getSender().getBankName(), transaction.getSender().getBranch()};
		if(Arrays.asList(fields).contains(null) || Arrays.asList(fields).contains("") ) return false;
		return true;
	}
	

	public void persist(List<Transaction> validTransactions) {

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Import imp = new Import(validTransactions.get(0).getDateTime().toLocalDate(), user);
		iRepository.save(imp);
		validTransactions.forEach(x -> x.setUpload(imp));
		validTransactions.forEach(tRepository::save);

	}



}
