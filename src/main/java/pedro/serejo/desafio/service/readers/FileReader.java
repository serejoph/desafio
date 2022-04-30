package pedro.serejo.desafio.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import pedro.serejo.desafio.model.Transaction;

public interface FileReader {

	public List<Transaction> getTransactions(MultipartFile file) throws IOException;
	
}
