package pedro.serejo.desafio.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import pedro.serejo.desafio.model.Transaction;

@Component
public class CSVReader implements FileReader {

	@Override
	public List<Transaction> getTransactions(MultipartFile file) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
		String[] csvLines = br.lines().toArray(x -> new String[x]);
		List<Transaction> transactions = Arrays.asList(csvLines).stream().map(this::instantiateTransaction)
				.filter(x -> x != null).collect(Collectors.toList());

		return transactions;
	}

	private Transaction instantiateTransaction(String transactionString) {
		try {
			String[] fields = transactionString.split(",");
			LocalDateTime transactionDateTime = LocalDateTime.parse(fields[7]);
			Transaction transaction = new Transaction(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5],
					new BigDecimal(fields[6]), transactionDateTime);
			return transaction;
		} catch (IndexOutOfBoundsException | DateTimeParseException | NumberFormatException e) {
			return null;
		}
	}
}
