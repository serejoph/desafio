package pedro.serejo.desafio.service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pedro.serejo.desafio.controller.dto.SuspectTransactionsDto;
import pedro.serejo.desafio.model.BankAccount;
import pedro.serejo.desafio.model.Transaction;
import pedro.serejo.desafio.repositories.TransactionRepository;

@Service
public class TransactionAnalyzer {

	@Autowired
	TransactionRepository transactionRepo;

	public SuspectTransactionsDto analyze(YearMonth yearMonth) {

		List<Transaction> monthlyTransactions = transactionRepo.findByYearMonth(yearMonth);
		
		if (monthlyTransactions.isEmpty()) return null;

		Map<BankAccount, BigDecimal> accountsMap = getAccountsMap(monthlyTransactions);
		Map<String, BigDecimal> branchesMap = getBranchesMap(monthlyTransactions);

		List<Transaction> suspectTransactions = monthlyTransactions.stream()
				.filter(x -> x.getAmmount().compareTo(new BigDecimal("100000")) >= 0)
				.collect(Collectors.toList());

		Map<BankAccount, BigDecimal> suspectAccounts = accountsMap.entrySet().stream()
				.filter(x -> x.getValue().compareTo(new BigDecimal("1000000")) >= 0)
				.collect(Collectors.toMap(x->x.getKey(), x->x.getValue()));

		Map<String, BigDecimal> suspectBranches = branchesMap.entrySet().stream()
				.filter(x -> x.getValue().compareTo(new BigDecimal("1000000000"))>=0).peek(System.out::println)
				.collect(Collectors.toMap(x -> x.getKey(), x->x.getValue()));

		
		return new SuspectTransactionsDto(suspectAccounts, suspectTransactions, suspectBranches);
	}

	private Map<BankAccount, BigDecimal> getAccountsMap(List<Transaction> monthlyTransactions) {

		Map<BankAccount, BigDecimal> accountsMap = new HashMap<>();
		for (Transaction t : monthlyTransactions) {
			accountsMap.merge(t.getSender(), t.getAmmount(),
					(account, ammount) -> ammount.add(accountsMap.get(t.getSender())));
			accountsMap.merge(t.getRecipient(), t.getAmmount(),
					(account, ammount) -> ammount.add(accountsMap.get(t.getRecipient())));
		}
		return accountsMap;
	}

	private Map<String, BigDecimal> getBranchesMap(List<Transaction> monthlyTransactions) {

		Map<String, BigDecimal> branchesMap = new HashMap<>();

		for (Transaction t : monthlyTransactions) {

			String senderBranch = t.getSender().getBankName() + "," + t.getSender().getBranch();
			String recipientBranch = t.getRecipient().getBankName() + "," + t.getRecipient().getBranch();
			branchesMap.merge(senderBranch, t.getAmmount(),
					(branch, ammount) -> ammount.add(branchesMap.get(senderBranch)));
			branchesMap.merge(recipientBranch, t.getAmmount(),
					(branch, ammount) -> ammount.add(branchesMap.get(recipientBranch)));

		}
		return branchesMap;

	}

}
