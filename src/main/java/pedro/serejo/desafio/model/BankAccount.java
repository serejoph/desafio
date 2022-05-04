package pedro.serejo.desafio.model;

import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class BankAccount {

	private String bankName;
	private String branch;
	private String account;
	
	public BankAccount(){
		
	}
	
	public BankAccount(String bankName, String branch, String account) {
		this.bankName = bankName;
		this.branch = branch;
		this.account = account;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "BankAccount [bankName=" + bankName + ", branch=" + branch + ", account=" + account + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(account, bankName, branch);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		return Objects.equals(account, other.account) && Objects.equals(bankName, other.bankName)
				&& Objects.equals(branch, other.branch);
	}
	
	
	
	
}
