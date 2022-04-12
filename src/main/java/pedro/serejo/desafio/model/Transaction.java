package pedro.serejo.desafio.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private	Long id;
	
	private String senderBank;
	private String senderBranch;
	private String senderAccount;
	private String recipientBank;
	private String recipientBranch;
	private String recipientAccount;
	private BigDecimal ammount;
	private LocalDateTime dateTime;
	
	public String getSenderBank() {
		return senderBank;
	}
	public void setSenderBank(String senderBank) {
		this.senderBank = senderBank;
	}
	public String getSenderBranch() {
		return senderBranch;
	}
	public void setSenderBranch(String senderBranch) {
		this.senderBranch = senderBranch;
	}
	public String getSenderAccount() {
		return senderAccount;
	}
	public void setSenderAccount(String senderAccount) {
		this.senderAccount = senderAccount;
	}
	public String getRecipientBank() {
		return recipientBank;
	}
	public void setRecipientBank(String recipientBank) {
		this.recipientBank = recipientBank;
	}
	public String getRecipientBranch() {
		return recipientBranch;
	}
	public void setRecipientBranch(String recipientBranch) {
		this.recipientBranch = recipientBranch;
	}
	public String getRecipientAccount() {
		return recipientAccount;
	}
	public void setRecipientAccount(String recipientAccount) {
		this.recipientAccount = recipientAccount;
	}
	public BigDecimal getAmmount() {
		return ammount;
	}
	public void setAmmount(BigDecimal ammount) {
		this.ammount = ammount;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	@Override
	public String toString() {
		return "Transaction [senderBank=" + senderBank + ", senderBranch=" + senderBranch + ", senderAccount="
				+ senderAccount + ", recipientBank=" + recipientBank + ", recipientBranch=" + recipientBranch
				+ ", recipientAccount=" + recipientAccount + ", ammount=" + ammount + ", dateTime=" + dateTime + "]";
	}
	public Transaction(String senderBank, String senderBranch, String senderAccount, String recipientBank,
			String recipientBranch, String recipientAccount, BigDecimal ammount, LocalDateTime dateTime) {
		
		this.senderBank = senderBank;
		this.senderBranch = senderBranch;
		this.senderAccount = senderAccount;
		this.recipientBank = recipientBank;
		this.recipientBranch = recipientBranch;
		this.recipientAccount = recipientAccount;
		this.ammount = ammount;
		this.dateTime = dateTime;
	}
	
	public Transaction() {
		
	}
	
	
}
