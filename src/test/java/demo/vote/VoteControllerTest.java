package demo.vote;

import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
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

import demo.DemoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
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
						content()
								.contentTypeCompatibleWith(MediaType.TEXT_HTML))
				.andExpect(
						content().string(
								stringContainsInOrder(Arrays.asList(
										"Le Comte de Monte-Cristo",
										"Le Petit Prince"))));
	}

	@Test
	@Ignore
	public void testPostVoteNoLivro() throws Exception {
		mvc.perform(post("/vote-no-livro", "session_id", "book_id"))
				.andExpect(view().name("vote"))
				.andExpect(status().isOk())
				.andExpect(
						content().string(
								stringContainsInOrder(Arrays.asList(
										"Le Comte de Monte-Cristo",
										"The Da Vinci Code"))));
	}

}