package pedro.serejo.desafio.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pedro.serejo.desafio.model.Transaction;

@Service
public class ParsingService {

	public List<Transaction> getTransactions(MultipartFile file)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException, IOException {

		
		String fileName = file.getContentType();
		String fileType = fileName.substring(fileName.length() - 3).toUpperCase();
		FileReader reader = (FileReader)Class.forName("pedro.serejo.desafio.service." + fileType + "Reader").getConstructor()
				.newInstance();
		List<Transaction> transactions = reader.getTransactions(file);
		return transactions;
	}

}
