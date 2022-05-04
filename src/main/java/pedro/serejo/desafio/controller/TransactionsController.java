package pedro.serejo.desafio.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import pedro.serejo.desafio.controller.dto.SuspectTransactionsDto;
import pedro.serejo.desafio.exceptions.UploadException;
import pedro.serejo.desafio.model.Import;
import pedro.serejo.desafio.model.Transaction;
import pedro.serejo.desafio.repositories.ImportRepository;
import pedro.serejo.desafio.repositories.TransactionRepository;
import pedro.serejo.desafio.service.ParsingService;
import pedro.serejo.desafio.service.TransactionAnalyzer;
import pedro.serejo.desafio.service.TransactionsValidator;

@Controller
@RequestMapping("upload")
public class TransactionsController {

	@Autowired
	ParsingService parsingService;
	@Autowired
	TransactionsValidator transactionsService; 
	@Autowired
	TransactionRepository tRepository;
	@Autowired
	ImportRepository iRepository;
	@Autowired
	TransactionAnalyzer tAnalyzer;

	@RequestMapping("form")
	public String getUploadForm(Model model) {
		
		List<Import> imports = iRepository.findAll();
		model.addAttribute("imports", imports);
		
		return "UploadForm";
	}

	@PostMapping("uploadFile")
	public Object uploadCsvFile(Model model, MultipartFile file)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException
			{

		if (file.isEmpty()) throw new UploadException("O arquivo enviado está vazio");
		List<Transaction> transactions = parsingService.getTransactions(file);
		List<Transaction> validTransactions = transactionsService.validate(transactions);
		transactionsService.persist(validTransactions);
		

		return new RedirectView("/upload/form");
	}



	@ExceptionHandler(value = UploadException.class)
	public String uploadExceptionHandler(Model model, UploadException e) {
		
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
	
	@GetMapping("analyze")
	public String analyzeForm() {
		return "AnalyzeTransactions";
	}
	
	@PostMapping("analyze")
	public String analyze(String month, Model model){
		YearMonth yearMonth = null;
		
		try{
			yearMonth = YearMonth.parse(month);
		}catch (DateTimeParseException e) {
			model.addAttribute("error", "Data inválida");
			return "AnalyzeTransactions";
		}
	
		SuspectTransactionsDto dto = tAnalyzer.analyze(yearMonth);
		
		if (dto == null) {
			model.addAttribute("error", String.format("Não foram encontradas importações no mês %s", yearMonth.format(DateTimeFormatter.ofPattern("MM/yyyy"))));
			return "AnalyzeTransactions";
		}
		
		model.addAttribute("dto", dto);
		
		return "AnalyzeTransactions";
	}

}
