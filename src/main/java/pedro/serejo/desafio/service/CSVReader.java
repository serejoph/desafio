package pedro.serejo.desafio.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import pedro.serejo.desafio.exceptions.CsvException;
import pedro.serejo.desafio.model.Transaction;
import pedro.serejo.desafio.repositories.TransactionRepository;

@Component
public class CSVReader implements FileReader{

	@Autowired
	TransactionRepository rep;

	public List<Transaction> validate(String[] csvLines) {

		checkIfEmpty(csvLines);
		LocalDate firstLocalDate = parseFirstDate(csvLines);

		List<Transaction> validTransactions = new ArrayList<>();

		l: for (String line : csvLines) {

			String[] fields = line.split(",");
			for (String field : fields) {
				if (field.isBlank())
					continue l;
			}
			try {
				String dateTimeField = fields[7];
				LocalDateTime localDateTime = LocalDateTime.parse(dateTimeField);
				LocalDate localDate = localDateTime.toLocalDate();

				if (localDate.equals(firstLocalDate)) {

					Transaction t = new Transaction(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5],
							new BigDecimal(fields[6]), localDateTime);

					validTransactions.add(t);
				}
			} catch (IndexOutOfBoundsException | DateTimeParseException | NumberFormatException e) {
				continue;
			}
			

		}

		return validTransactions;
	}

	private LocalDate parseFirstDate(String[] csvLines) {
		try {
			String firstDateString = csvLines[0].split(",")[7];
			LocalDate firstLocalDate = LocalDateTime.parse(firstDateString).toLocalDate();
			
			
			if (rep.findByLocalDate(firstLocalDate).size() > 0) {
				var dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				throw new CsvException("As transações do dia " + dtf.format(firstLocalDate) + " já foram registradas");
			}

			return firstLocalDate;
		} catch (IndexOutOfBoundsException | DateTimeParseException e) {
			throw new CsvException("Não foi possível identificar a data das transações");
		}

	}

	private void checkIfEmpty(String[] csvLines) {
		if (csvLines.length == 0)
			throw new CsvException("O arquivo está vazio");
	}

	@Override
	public List<Transaction> getTransactions(MultipartFile file) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
		String[] csvLines = br.lines().toArray(x -> new String[x]);
		List<Transaction> transactions =  Arrays.asList(csvLines).stream().map(this::instantiateTransaction).collect(Collectors.toList());
		
		return transactions;
	}
	
	private Transaction instantiateTransaction(String transactionString) {
		String[] fields = transactionString.split(",");
		LocalDateTime localDateTime = LocalDateTime.parse(fields[7]);
		Transaction transaction = new Transaction(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], new BigDecimal(fields[6]), localDateTime);
		return transaction;
	}
}
