package pedro.serejo.desafio.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity

public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@AttributeOverrides({ @AttributeOverride(name = "bankName", column = @Column(name = "senderBank")),
			@AttributeOverride(name = "branch", column = @Column(name = "senderBranch")),
			@AttributeOverride(name = "account", column = @Column(name = "senderAccount")) })
	@Embedded
	private BankAccount sender;
	@AttributeOverrides({ @AttributeOverride(name = "bankName", column = @Column(name = "recipientBank")),
		@AttributeOverride(name = "branch", column = @Column(name = "recipientBranch")),
		@AttributeOverride(name = "account", column = @Column(name = "recipientAccount")) })
	@Embedded
	private BankAccount recipient;
	private BigDecimal ammount;
	private LocalDateTime dateTime;
	@ManyToOne
	private Import upload;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Import getUpload() {
		return upload;
	}

	public void setUpload(Import imp) {
		this.upload = imp;
	}

	public BankAccount getSender() {
		return sender;
	}

	public void setSender(BankAccount sender) {
		this.sender = sender;
	}

	public BankAccount getRecipient() {
		return recipient;
	}

	public void setRecipient(BankAccount recipient) {
		this.recipient = recipient;
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
		return "Transaction [id=" + id + ", sender=" + sender + ", recipient=" + recipient + ", ammount=" + ammount
				+ ", dateTime=" + dateTime + ", upload=" + upload + "]";
	}

	public Transaction(BankAccount sender, BankAccount recipient, BigDecimal ammount, LocalDateTime dateTime) {
		super();
		this.sender = sender;
		this.recipient = recipient;
		this.ammount = ammount;
		this.dateTime = dateTime;

	}

	public Transaction() {

	}

}
