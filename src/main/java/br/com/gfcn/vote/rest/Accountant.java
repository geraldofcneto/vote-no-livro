package br.com.gfcn.vote.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gfcn.book.Book;
import br.com.gfcn.book.BookRepository;
import br.com.gfcn.util.JsonUtil;
import br.com.gfcn.vote.VoteSessionResult;
import br.com.gfcn.vote.VoteSessionResultRepository;

@Service
public class Accountant {

	@Autowired
	VoteSessionResultRepository resultRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	public Map<String, Long> count() {
		Map<String, Long> map = new HashMap<>();
		for (Book book : books()) {
			
			map.put(JsonUtil.asJsonString(book), votesFor(book));
		}
		return map;
	}

	private Long votesFor(Book book) {
		List<VoteSessionResult> results = resultRepository.findByBook(book);
		Long votes = 0l;
		for (VoteSessionResult result : results) {
			votes += numberOfBooks() - result.getPosition();
		}
		return votes;
	}

	private Iterable<Book> books() {
		return bookRepository.findAll();
	}

	private long numberOfBooks() {
		return bookRepository.count();
	}

}
