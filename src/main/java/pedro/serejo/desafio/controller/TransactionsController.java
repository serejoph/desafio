package pedro.serejo.desafio.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import pedro.serejo.desafio.exceptions.CsvException;
import pedro.serejo.desafio.model.Import;
import pedro.serejo.desafio.model.Transaction;
import pedro.serejo.desafio.model.User;
import pedro.serejo.desafio.repositories.ImportRepository;
import pedro.serejo.desafio.repositories.TransactionRepository;
import pedro.serejo.desafio.service.CSVReader;
import pedro.serejo.desafio.service.ParsingService;

@Controller
@RequestMapping("upload")
public class TransactionsController {

	@Autowired
	ParsingService fileReader;
	@Autowired
	CSVReader csvVal;
	@Autowired
	TransactionRepository tRepository;
	@Autowired
	ImportRepository iRepository;

	@RequestMapping("form")
	public String getUploadForm(Model model) {
		
		List<Import> imports = iRepository.findAll();
		model.addAttribute("imports", imports);
		
		return "UploadForm";
	}

	@PostMapping("uploadFile")
	public Object uploadCsvFile(Model model, MultipartFile file)
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		List<Transaction> transactions = fileReader.getTransactions(file);
		
		
		
		
		
		
	
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
		String[] csvLines = br.lines().toArray(x -> new String[x]);
		List<Transaction> validTransactions = csvVal.validate(csvLines);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Import imp = new Import(validTransactions.get(0).getDateTime().toLocalDate(), user);
		iRepository.save(imp);
		validTransactions.forEach(x-> x.setUpload(imp));
		validTransactions.forEach(tRepository::save);
		

		return new RedirectView("/upload/form");
	}

	@ExceptionHandler()
	public String csvValidationHandler(Model model, CsvException e) {
		
		model.addAttribute("error", e.getMessage());
		return "forward:form";
	}
	
	@GetMapping("detail/{id}")
	public String detailImport(@PathVariable Long id, Model model) {
		
		
		List<Transaction> transactions = tRepository.findByUploadId(id);
		Import imp =iRepository.findById(id).get();
		model.addAttribute("import", imp);
		model.addAttribute("transactions", transactions);
		
		return "ImportDetails";
	}

}
