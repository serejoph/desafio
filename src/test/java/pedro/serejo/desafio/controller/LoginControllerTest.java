package pedro.serejo.desafio.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LoginControllerTest {

	@Autowired
	MockMvc mock;
	
	@Test
	void loginComUsuarioESenhaCorretosDeveRedirecionarTransactions() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/login")
				.param("email", "admin@email.com.br").param("password", "123999");
		mock.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.redirectedUrl("/transactions"));
	}
	
	@Test
	void loginComUsuarioInexistenteDeveRedirecionarLoginErrorTrue() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/login")
				.param("email", "inexistente@email.com.br").param("password", "123999");
		mock.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.redirectedUrl("/login?error=true"));
	}
	
	@Test
	void loginComSenhaIncorretaDeveRedirecionarLoginErrorTrue() throws Exception {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/login")
				.param("email", "admin@email.com.br").param("password", "1123999");
		mock.perform(request).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.redirectedUrl("/login?error=true"));
	}

}
