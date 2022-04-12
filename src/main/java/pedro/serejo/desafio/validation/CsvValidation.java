package pedro.serejo.desafio.validation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CsvValidation {

	public boolean validate(String[] csvLines) {

		if (csvLines.length == 0)
			return false;
		if (!checkDates(csvLines))
			return false;
		return true;

	}

	
	// Identifica a data da primeira transação e verifica se todas as demais ocorreram no mesmo dia.	 
	private boolean checkDates(String[] csvLines) {	
		try {
			String firstDateString = csvLines[0].split(",")[7];
			LocalDate firstLocalDate = LocalDateTime.parse(firstDateString).toLocalDate();

			for (String s : csvLines) {
				String[] fields = s.split(",");
				String dateTimeField = fields[7];
				LocalDate localDate = LocalDateTime.parse(dateTimeField).toLocalDate();
				if (!localDate.equals(firstLocalDate)) return false;
			}
			
		} catch (DateTimeParseException | IndexOutOfBoundsException e) {
			return false;
		}

		return true;
	}

}
