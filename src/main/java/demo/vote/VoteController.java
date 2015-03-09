package demo.vote;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import demo.book.Book;
import demo.book.BookRepository;

@Controller
public class VoteController {

	@Autowired
	BookRepository bookRepository;

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

		List<Book> books = Arrays.asList(
				bookRepository.title("Le Comte de Monte-Cristo"),
				bookRepository.title("Le Petit Prince"));

		model.addAttribute("books", books);
		model.addAttribute("session_id", sessionId);

		return "vote";
	}

	@RequestMapping(value = "/vote-no-livro", method = RequestMethod.POST)
	public String postVote(
			@RequestParam(value = "session_id", required = true) String sessionId,
			@RequestParam(value = "book_id", required = true) Long bookId,
			Model model) {

		model.addAttribute("session_id", sessionId);
		model.addAttribute("book_id", bookId);

		return "vote-result";
	}
}