package pedro.serejo.desafio.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import pedro.serejo.desafio.exceptions.TransactionsAlreadySavedException;
import pedro.serejo.desafio.model.Transaction;
import pedro.serejo.desafio.repositories.TransactionRepository;
import pedro.serejo.desafio.validation.CsvValidator;


@Controller
@RequestMapping("upload")
public class UploadController {

	@Autowired
	CsvValidator csvVal;
	@Autowired
	TransactionRepository repo;
	
	@GetMapping("form")
	public String getUploadForm() {
		return "UploadForm";
	}

	@PostMapping("uploadFile")
	public Object uploadCsvFile(Model model, MultipartFile file) throws IOException {

		
		BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
		String[] csvLines = br.lines().toArray(x-> new String[x]);
		
		
		if (csvLines.length == 0)	{
			model.addAttribute("error", "O arquivo enviado está vazio");
			return "UploadForm";
		}
		try {
		List<Transaction> validTransactions = csvVal.validate(csvLines);
		System.out.println("Transações válidas: \n\n\n");
		validTransactions.forEach(System.out::println);
		validTransactions.forEach(repo::save);
		}catch (TransactionsAlreadySavedException e) {
			model.addAttribute("error", "As transações do dia "+ e.getMessage() + " já foram registradas.");
			return "UploadForm";
		}
		
		
		return new RedirectView("/upload/form");
	}

}
