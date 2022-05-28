package pedro.serejo.desafio.controller;

import static org.mockito.Mockito.times;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import pedro.serejo.desafio.model.Import;
import pedro.serejo.desafio.model.Transaction;
import pedro.serejo.desafio.model.User;
import pedro.serejo.desafio.repositories.ImportRepository;
import pedro.serejo.desafio.repositories.TransactionRepository;
import pedro.serejo.desafio.service.ParsingService;
import pedro.serejo.desafio.service.TransactionAnalyzer;
import pedro.serejo.desafio.service.TransactionsValidator;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TransactionsControllerTest {

	@MockBean
	ParsingService parsingService;
	@MockBean
	TransactionsValidator transactionsValidator;
	@MockBean
	TransactionRepository tRepository;
	@MockBean
	ImportRepository iRepository;
	@MockBean
	TransactionAnalyzer tAnalyzer;
	@Autowired
	MockMvc mock;
	@Captor
	ArgumentCaptor<String> captor;

	@BeforeEach
	private void beforeEach() {
		MockitoAnnotations.openMocks(this);
		
		
	}
	
	@Test
	@WithMockUser(username = "admin@email.com.br", password = "123999")
	void requisicaoListaDeTransacoes() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/transactions"))
				.andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(iRepository).findAll();
	}

	@Test
	@WithMockUser(username = "admin@email.com.br", password = "123999")
	void uploadDeArquivo() throws Exception {
		List<Transaction> transactions = Arrays.asList(new Transaction(), new Transaction());	
		List<Transaction> validTransactions = Arrays.asList(transactions.get(0));
		MockMultipartFile file = new MockMultipartFile("file",
				new FileInputStream(new File("src/test/resources/exemplo.csv")));
		
		Mockito.when(parsingService.getTransactions(file)).thenReturn(transactions);
		Mockito.when(transactionsValidator.validate(transactions)).thenReturn(validTransactions);
		
		mock.perform(MockMvcRequestBuilders.multipart("/transactions/upload").file(file));
		
		
		Mockito.verify(parsingService, times(1)).getTransactions(file);
		Mockito.verify(transactionsValidator, times(1)).validate(transactions);
		Mockito.verify(transactionsValidator, times(1)).persist(validTransactions);
	
	}
	
	@Test
	@WithMockUser(username = "admin@email.com.br", password = "123999")
	void detalharTransacao() throws Exception {
		Import imp = new Import();
		imp.setId(1l);
		imp.setImportDateTime(LocalDateTime.now());
		imp.setTransactionsDate(LocalDate.now());
		imp.setUser(new User());
		Mockito.when(iRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(imp));
		
		mock.perform(MockMvcRequestBuilders.get("/transactions/detail/10")).andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(tRepository).findByUploadId(10l);
		Mockito.verify(iRepository).findById(10l);
		
	}
	
	@Test
	@WithMockUser(username = "admin@email.com.br", password = "123999")
	void analizarTransacao() throws Exception {
		String yearMonthString = "1990-12";
		mock.perform(MockMvcRequestBuilders.post("/transactions/analyze").param("month", yearMonthString));
		Mockito.verify(tAnalyzer).analyze(captor.capture());
		Assertions.assertEquals(yearMonthString, captor.getValue());
		
		
	}
	
}
