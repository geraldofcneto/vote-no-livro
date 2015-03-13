package br.com.gfcn.vote;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.gfcn.Application;
import br.com.gfcn.book.BookRepository;
import br.com.gfcn.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class VoteControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private VoteSessionRepository voteSessionRepository;

	@Autowired
	private VoteSessionHandler voteSessionHandler;

	@Autowired
	VoteSessionResultRepository voteSessionResultRepository;
	
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
	public void testGetJsonVoteNoLivro() throws Exception {
		mvc.perform(get("/api/vote-no-livro"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentTypeCompatibleWith(
								MediaType.APPLICATION_JSON))
				.andExpect(
						content()
								.string(containsString("\"session\":{\"id\":2,\"votes\":[],\"result\":null}")))
				.andExpect(
						content()
								.string(containsString("{\"id\":1,\"title\":\"Le Comte de Monte-Cristo\",\"author\":\"Alexandre Dumas, pére\"}")))
				.andExpect(
						content()
								.string(containsString("{\"id\":2,\"title\":\"The Da Vinci Code\",\"author\":\"Dan Brown\"}")));

	}

	@Test
	public void testSorting() throws Exception {
		VoteSession session = voteSessionRepository.save(new VoteSession());

		postVote(1l, 2l, session);
		postVote(1l, 3l, session);
		postVote(1l, 4l, session);
		postVote(1l, 5l, session);
		postVote(2l, 3l, session);
		postVote(2l, 4l, session);
		postVote(2l, 5l, session);
		postVote(3l, 4l, session);
		postVote(3l, 5l, session);
		postVote(4l, 5l, session);

		session = voteSessionRepository.findOne(session.getId());

		Assert.assertTrue(voteSessionHandler.isFinished(session));

		Assert.assertEquals(5, voteSessionResultRepository.count());
	}

	private ResultActions postVote(long win, long lose, VoteSession session)
			throws Exception {
		return mvc.perform(
				post("/api/vote-no-livro").content(jsonRequestData(win, lose, session)).contentType(
						MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	private String jsonRequestData(long win, long lose, VoteSession session) {
		return JsonUtil.asJsonString(new Request(
		bookRepository.findOne(win), bookRepository.findOne(lose), session));
	}

}