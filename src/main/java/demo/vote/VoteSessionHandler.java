package demo.vote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.book.Book;
import demo.book.BookRepository;
import demo.util.IterableUtil;

@Component
public class VoteSessionHandler {

	@Autowired
	VoteSessionRepository voteSessionRepository;

	@Autowired
	BookRepository bookRepository;

	public boolean isFinished(List<Book> books, List<Vote> votes) {
		return !new BookComparator(books, votes).isTied();
	}

	public List<Book> sort(List<Vote> votes) {
		List<Book> books = booksVoted(votes);

		Collections.sort(books, new BookComparator(votes));

		return books;
	}

	private List<Book> booksVoted(List<Vote> votes) {
		Set<Book> books = new HashSet<>();
		for (Vote vote : votes) {
			books.add(vote.getWinner());
			books.add(vote.getLoser());
		}
		return new ArrayList<>(books);
	}

	public Set<Book> tied(List<Vote> votes) {
		return new BookComparator(votes).getTied();
	}

	public Set<Book> tied(Long sessionId) {
		List<Vote> votes = voteSessionRepository.findOne(sessionId).getVotes();

		List<Book> books = new ArrayList<>(
				IterableUtil.makeList(bookRepository.findAll()));

		BookComparator bookComparator = new BookComparator(books, votes);

		return bookComparator.getFirstTied();
	}

	public boolean isFinished(List<Book> books, VoteSession session) {
		List<Vote> votes = session.getVotes();
		
		return isFinished(books, votes);
	}

}
