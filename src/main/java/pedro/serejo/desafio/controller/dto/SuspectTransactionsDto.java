package pedro.serejo.desafio.controller.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import pedro.serejo.desafio.model.BankAccount;
import pedro.serejo.desafio.model.Transaction;

public class SuspectTransactionsDto {
	
	Map<BankAccount, BigDecimal> accounts;
	List<Transaction> transactions;
	Map<String, BigDecimal> branches;
	
	
	public SuspectTransactionsDto(Map<BankAccount, BigDecimal> accounts, List<Transaction> transactions, Map<String, BigDecimal> branches) {
		this.accounts = accounts;
		this.transactions = transactions;
		this.branches = branches;
	}
	public Map<BankAccount, BigDecimal> getAccounts() {
		return accounts;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public Map<String, BigDecimal> getBranches() {
		return branches;
	}
	@Override
	public String toString() {
		return "SuspectedTransactionsDto [accounts=" + accounts + ", transactions=" + transactions + ", branches="
				+ branches + "]";
	}

	
	

}
