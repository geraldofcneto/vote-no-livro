package demo.vote.web;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import demo.book.Book;
import demo.book.BookRepository;
import demo.util.IterableUtil;
import demo.vote.Vote;
import demo.vote.VoteRepository;
import demo.vote.VoteSession;
import demo.vote.VoteSessionHandler;
import demo.vote.VoteSessionRepository;

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

		defineCandidates(model, session);

		return "vote";
	}

	private void defineCandidates(Model model, VoteSession session) {
		Set<Book> books = voteSessionHandler.tied(session.getId());

		model.addAttribute("books", books);
		model.addAttribute("session_id", session.getId());
	}

	private VoteSession findOrCreateSession(String sessionId) {
		return sessionId.isEmpty() ? 
				voteSessionRepository.save(new VoteSession()) : 
				voteSessionRepository.findOne(Long.parseLong(sessionId));
	}

	@RequestMapping(value = "/vote-no-livro", method = RequestMethod.POST)
	public String postVote(
			@RequestParam(value = "session_id", required = true) String sessionId,
			@RequestParam(value = "winner_book_id", required = true) Long winnerBookId,
			@RequestParam(value = "loser_book_id", required = true) Long loserBookId,
			Model model) {

		VoteSession session = voteSessionRepository.findOne(Long.parseLong(sessionId));
		voteRepository.save(new Vote(bookRepository.findOne(winnerBookId), bookRepository.findOne(loserBookId), session));
		
		
		
		if (voteSessionHandler.isFinished(IterableUtil.makeList(bookRepository.findAll()), session))
			return "vote-result";

		defineCandidates(model, session);
		
		return "vote";
	}
}