package br.com.gfcn.vote.web;

import java.util.Map;
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
import br.com.gfcn.vote.rest.Accountant;
import br.com.gfcn.vote.rest.NomineesResponse;
import br.com.gfcn.vote.rest.Response;
import br.com.gfcn.vote.rest.VoteResponse;
import br.com.gfcn.vote.rest.VoteResultResponse;

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

	@Autowired
	Accountant accountant;

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
	public @ResponseBody Response postJson(@RequestBody Vote vote) {
		System.out.println("Request: " + vote);

		vote = voteRepository.save(vote);

		return response(vote);
	}

	@RequestMapping(value = "/api/final-counting", method = RequestMethod.GET)
	public @ResponseBody Map<String, Long> getFinalCounting() {
		return (accountant.count());
	}

	private Response response(Vote vote) {
		if (voteSessionHandler
				.handleFinished(updatedSession(vote.getSession()))) {
			return new VoteResultResponse(updatedSession(vote.getSession()));
		}

		return new VoteResponse(updatedSession(vote.getSession()));
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