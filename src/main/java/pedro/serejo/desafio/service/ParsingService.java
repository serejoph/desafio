package pedro.serejo.desafio.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pedro.serejo.desafio.exceptions.CsvException;
import pedro.serejo.desafio.model.Transaction;

@Service
public class ParsingService {

	public List<Transaction> getTransactions(MultipartFile file)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, IOException {

		String fileName = file.getContentType();
		String fileType = fileName.substring(fileName.length() - 3).toUpperCase();

		if (file.isEmpty())
			throw new CsvException("O arquivo enviado está vazio");
		
		FileReader reader = (FileReader)Class.forName("pedro.serejo.desafio.service." + fileType + "Reader").getConstructor()
				.newInstance();
		

		return reader.getTransactions(file);
	}

}
