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

import org.hamcrest.Matcher;
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
import br.com.gfcn.book.Book;
import br.com.gfcn.book.BookRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		ResultActions last = postVote(4l, 5l, session);

//		last.andExpect(content().string("The end"));

		session = voteSessionRepository.findOne(session.getId());

		
		System.out.println("Fuckin tied: " + voteSessionHandler.tied(session.getId()));
		
		Assert.assertTrue(voteSessionHandler.isFinished(session));
	}

	private ResultActions postVote(long win, long lose, VoteSession session)
			throws Exception {
		String asJsonString = asJsonString(new Request(
				bookRepository.findOne(win), bookRepository.findOne(lose),
				session));

		return mvc.perform(
				post("/api/vote-no-livro").content(asJsonString).contentType(
						MediaType.APPLICATION_JSON)).andExpect(status().isOk());
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

	class Request {

		private Book winner;
		private Book loser;
		private VoteSession session;

		public Request(Book win, Book lose, VoteSession session) {
			this.winner = win;
			this.loser = lose;
			this.session = session;
		}

		public Book getWinner() {
			return winner;
		}

		public void setWinner(Book winner) {
			this.winner = winner;
		}

		public Book getLoser() {
			return loser;
		}

		public void setLoser(Book loser) {
			this.loser = loser;
		}

		public VoteSession getSession() {
			return session;
		}

		public void setSession(VoteSession session) {
			this.session = session;
		}

		@Override
		public String toString() {
			return "Request [winner=" + winner + ", loser=" + loser
					+ ", session=" + session + "]";
		}

	}

}