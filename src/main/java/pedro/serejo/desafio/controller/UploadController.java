package pedro.serejo.desafio.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import pedro.serejo.desafio.exceptions.CsvException;
import pedro.serejo.desafio.model.Import;
import pedro.serejo.desafio.model.Transaction;
import pedro.serejo.desafio.repositories.ImportRepository;
import pedro.serejo.desafio.repositories.TransactionRepository;
import pedro.serejo.desafio.validation.CsvValidator;

@Controller
@RequestMapping("upload")
public class UploadController {

	@Autowired
	CsvValidator csvVal;
	@Autowired
	TransactionRepository tRepository;
	@Autowired
	ImportRepository iRepository;

	@GetMapping("form")
	public String getUploadForm(Model model) {
		
		return "UploadForm";
	}

	@PostMapping("uploadFile")
	public Object uploadCsvFile(Model model, MultipartFile file) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
		String[] csvLines = br.lines().toArray(x -> new String[x]);
		List<Transaction> validTransactions = csvVal.validate(csvLines);
		validTransactions.forEach(tRepository::save);
		
		iRepository.save(new Import(validTransactions.get(0).getDateTime().toLocalDate()));

		return new RedirectView("/upload/form");
	}

	@ExceptionHandler
	public String csvValidationHandler(Model model, CsvException e) {
		
		model.addAttribute("error", e.getMessage());
		return "UploadForm";
	}

}
