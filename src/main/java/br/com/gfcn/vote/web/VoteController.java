package br.com.gfcn.vote.web;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.gfcn.book.Book;
import br.com.gfcn.book.BookRepository;
import br.com.gfcn.vote.Vote;
import br.com.gfcn.vote.VoteRepository;
import br.com.gfcn.vote.VoteSession;
import br.com.gfcn.vote.VoteSessionHandler;
import br.com.gfcn.vote.VoteSessionRepository;
import br.com.gfcn.vote.rest.NomineesResponse;
import br.com.gfcn.vote.rest.Response;
import br.com.gfcn.vote.rest.VoteResponse;

@Controller
public class VoteController {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	VoteRepository voteRepository;

	@Autowired
	VoteSessionHandler voteSessionHandler;

	@Autowired
	VoteSessionRepository voteSessionRepository;

	@RequestMapping("/greeting")
	public String greeting(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

	@RequestMapping("/livros")
	public String books(Model model) {
		Iterable<Book> books = bookRepository.findAll();
		model.addAttribute("books", books);
		return "books";
	}

	@RequestMapping(value = "/vote-no-livro", method = RequestMethod.GET)
	public String getVote(
			@RequestParam(value = "session_id", required = false, defaultValue = "") String sessionId,
			Model model) {
		VoteSession session = findOrCreateSession(sessionId);

		Set<Book> books = findNominees(session);

		model.addAttribute("nominees", books);
		model.addAttribute("session_id", session.getId());

		return "vote";
	}

	private Set<Book> findNominees(VoteSession session) {
		return voteSessionHandler.tied(session.getId());
	}

	private VoteSession findOrCreateSession(String sessionId) {
		return sessionId.isEmpty() ? voteSessionRepository
				.save(new VoteSession()) : voteSessionRepository.findOne(Long
				.parseLong(sessionId));
	}

	@RequestMapping(value = "/api/vote-no-livro", method = RequestMethod.GET)
	public @ResponseBody Response getJson(
			@RequestParam(value = "session_id", required = true, defaultValue = "") String sessionId) {
		return createResponse(sessionId);
	}

	@RequestMapping(value = "/api/vote-no-livro", method = RequestMethod.POST)
	public @ResponseBody Response postJson(@RequestBody Vote request) {
		System.out.println("Request: " + request);

		voteRepository.save(new Vote(request.getWinner(), request.getLoser(),
				request.getSession()));

		VoteSession updatedSession = updatedSession(request.getSession());
		voteSessionHandler.verifySessionEnded(updatedSession);

		return new VoteResponse(updatedSession);
	}

	private VoteSession updatedSession(VoteSession session) {
		return voteSessionRepository.findOne(session.getId());
	}

	private Response createResponse(String sessionId) {
		VoteSession session = findOrCreateSession(sessionId);
		Set<Book> books = findNominees(session);
		return new NomineesResponse(session, books);
	}
}