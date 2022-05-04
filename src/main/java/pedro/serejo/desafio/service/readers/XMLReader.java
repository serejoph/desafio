package pedro.serejo.desafio.service.readers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.web.multipart.MultipartFile;

import pedro.serejo.desafio.model.BankAccount;
import pedro.serejo.desafio.model.Transaction;

public class XMLReader implements FileReader {

	@Override
	public List<Transaction> getTransactions(MultipartFile file) throws IOException {

		try {

			SAXReader s = new SAXReader();
			Document doc = s.read(file.getInputStream());
			List<Node> nodeTransactions = doc.selectSingleNode("transacoes").selectNodes("transacao");
			List<Transaction> transactions = nodeTransactions.stream().map(this::instantiateTransaction).filter(x -> x != null)
					.collect(Collectors.toList());
			return transactions;

		} catch (NullPointerException | DocumentException e) {
			return null;
		}

	}

	private Transaction instantiateTransaction(Node transaction) {
		
		try {
		Node senderNode = transaction.selectSingleNode("origem");
		Node recipientNode = transaction.selectSingleNode("destino");
		BankAccount sender = instantiateBank(senderNode);
		BankAccount recipient = instantiateBank(recipientNode);

		String ammount = transaction.selectSingleNode("valor").getText();
		String date = transaction.selectSingleNode("data").getText();

		return new Transaction(sender, recipient, new BigDecimal(ammount), LocalDateTime.parse(date));
	} catch (NullPointerException e) {
			return null;
		}
	}

	private BankAccount instantiateBank(Node bankNode) {
		try {
			String bankName = bankNode.selectSingleNode("banco").getText();
			String bankBranch = bankNode.selectSingleNode("agencia").getText();
			String account = bankNode.selectSingleNode("conta").getText();
			return new BankAccount(bankName, bankBranch, account);
		} catch (NullPointerException e) {
			return null;
		}
	}

}
