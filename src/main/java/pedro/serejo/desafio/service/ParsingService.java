package pedro.serejo.desafio.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pedro.serejo.desafio.model.Transaction;
import pedro.serejo.desafio.service.readers.FileReader;

@Service
public class ParsingService {

	public List<Transaction> getTransactions(MultipartFile file)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException, IOException {
		
		String fileType = file.getContentType();
		String fileExtension = fileType.substring(fileType.length() - 3).toUpperCase();
		FileReader reader = (FileReader)Class.forName("pedro.serejo.desafio.service.readers." + fileExtension + "Reader").getConstructor()
				.newInstance();
		
		
		
		List<Transaction> transactions = reader.getTransactions(file);
		return transactions;
	}

}
