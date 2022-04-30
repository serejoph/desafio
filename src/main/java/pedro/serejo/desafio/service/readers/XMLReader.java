package pedro.serejo.desafio.service.readers;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import pedro.serejo.desafio.model.Transaction;

public class XMLReader implements FileReader{

	@Override
	public List<Transaction> getTransactions(MultipartFile file) throws IOException {
				
		return null;
	}

}
