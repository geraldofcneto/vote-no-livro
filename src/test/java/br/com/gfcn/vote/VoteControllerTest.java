package br.com.gfcn.vote;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.gfcn.Application;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class VoteControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mvc;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testGetLivros() throws Exception {
		List<String> books = Arrays.asList("Le Comte de Monte-Cristo",
				"The Da Vinci Code", "Le Petit Prince", "Het Achterhuis",
				"The Perks of Being a Wallflower");

		mvc.perform(get("/livros")).andExpect(status().isOk())
				.andExpect(content().string(stringContainsInOrder(books)));
	}

	@Test
	public void testGetVoteNoLivro() throws Exception {
		mvc.perform(get("/vote-no-livro"))
				.andExpect(view().name("vote"))
				.andExpect(status().isOk())
				.andExpect(
						content().string(
								containsString("Le Comte de Monte-Cristo")))
				.andExpect(
						content().string(containsString("The Da Vinci Code")));
	}

	@Test
	public void testGetJsonVoteNoLivro() throws Exception {
		mvc.perform(get("/api/vote-no-livro"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentTypeCompatibleWith(
								MediaType.APPLICATION_JSON))
				.andExpect(
						content()
								.string(containsString("\"session\":{\"id\":2,\"votes\":[]}")))
				.andExpect(
						content()
								.string(containsString("{\"id\":1,\"title\":\"Le Comte de Monte-Cristo\",\"author\":\"Alexandre Dumas, pére\"}")))
				.andExpect(
						content()
								.string(containsString("{\"id\":2,\"title\":\"The Da Vinci Code\",\"author\":\"Dan Brown\"}")))
				.andExpect(
						content().string(containsString("\"finished\":false}")));

	}
	
	@Test
	public void testSorting() throws Exception {
		mvc.perform(post("/api/vote-no-livro").content(asJsonString(new Vote())));
	}
	
	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}