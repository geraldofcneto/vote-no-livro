package demo.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@RequestMapping("/vote-no-livro")
	public String vote(
			@RequestParam(value = "sessionId", required = false, defaultValue = "") String sessionId,
			Model model) {

		model.addAttribute("sessionId", sessionId);

		return "vote";
	}

	public void vote() {
		
	}
}