package pedro.serejo.desafio.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pedro.serejo.desafio.controller.dto.SuspectTransactionsDto;
import pedro.serejo.desafio.exceptions.AnalyzeException;
import pedro.serejo.desafio.exceptions.UploadException;
import pedro.serejo.desafio.model.Import;
import pedro.serejo.desafio.model.Transaction;
import pedro.serejo.desafio.repositories.ImportRepository;
import pedro.serejo.desafio.repositories.TransactionRepository;
import pedro.serejo.desafio.service.ParsingService;
import pedro.serejo.desafio.service.TransactionAnalyzer;
import pedro.serejo.desafio.service.TransactionsValidator;

@Controller
@RequestMapping("transactions")
public class TransactionsController {

	@Autowired
	ParsingService parsingService;
	@Autowired
	TransactionsValidator transactionsValidator;
	@Autowired
	TransactionRepository tRepository;
	@Autowired
	ImportRepository iRepository;
	@Autowired
	TransactionAnalyzer tAnalyzer;

	@RequestMapping
	public String listTransactions(Model model) {

		List<Import> imports = iRepository.findAll();
		model.addAttribute("imports", imports);

		return "Transactions";
	}

	@PostMapping("upload")
	public String uploadFile(Model model, MultipartFile file, HttpServletResponse res)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, IOException {

		List<Transaction> transactions = parsingService.getTransactions(file);
		List<Transaction> validTransactions = transactionsValidator.validate(transactions);
		transactionsValidator.persist(validTransactions);

		
		
		return "redirect:/transactions";
	}

	@GetMapping("detail/{id}")
	public String detailImport(@PathVariable Long id, Model model) {

		List<Transaction> transactions = tRepository.findByUploadId(id);
		Import imp = iRepository.findById(id).get();
		model.addAttribute("import", imp);
		model.addAttribute("transactions", transactions);
		return "ImportDetails";
	}

	@GetMapping("analyze")
	public String analyzeForm() {
		return "AnalyzeTransactions";
	}

	@PostMapping("analyze")
	public String analyze(String month, Model model) {

		SuspectTransactionsDto dto = tAnalyzer.analyze(month);
		model.addAttribute("dto", dto);

		return "AnalyzeTransactions";
	}

	@ExceptionHandler(value = UploadException.class)
	public String uploadExceptionHandler(UploadException e, HttpServletRequest request, RedirectAttributes red) {

		red.addFlashAttribute("error", e.getMessage());
		return "redirect:/transactions";
	}
	
	@ExceptionHandler(value = AnalyzeException.class)
	public String analyzerExceptionHandler(AnalyzeException e, HttpServletRequest request, RedirectAttributes red) {

		red.addFlashAttribute("error", e.getMessage());
		return "redirect:/transactions/analyze";
	}

}
