package pedro.serejo.desafio.controller;

import static org.mockito.Mockito.times;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

	@Test
	@WithMockUser(username = "admin@email.com.br", password = "123999")
	void listaDeImportacoesPopulada() throws Exception {
		User user = new User();
		user.setEmail("pedro.serejo@email.com");
		user.setEnabled(true);
		user.setId(2l);
		user.setName("Pedro");
		user.setPassword("123");
		Import imp = new Import(LocalDate.now(), user);
		List<Import> imports = List.of(imp);
		Mockito.when(iRepository.findAll()).thenReturn(imports);
		mock.perform(MockMvcRequestBuilders.get("/transactions"))
				.andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(iRepository).findAll();
	}

	@Test
	@WithMockUser(username = "admin@email.com.br", password = "123999")
	void listaDeImportacoesVazia() throws Exception {
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
	void uploadDeArquivoVazio() throws Exception {

		MockMultipartFile file = new MockMultipartFile("file",
				new FileInputStream(new File("src/test/resources/vazio.csv")));
		mock.perform(MockMvcRequestBuilders.multipart("/transactions/upload").file(file));
		Mockito.verify(parsingService, Mockito.never()).getTransactions(file);

	}

}
