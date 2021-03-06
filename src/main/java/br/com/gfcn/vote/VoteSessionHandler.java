package br.com.gfcn.vote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gfcn.book.Book;
import br.com.gfcn.book.BookRepository;
import br.com.gfcn.util.IterableUtil;

@Component
public class VoteSessionHandler {

	@Autowired
	VoteSessionRepository voteSessionRepository;

	@Autowired
	VoteSessionResultRepository voteSessionResultRepository;
	
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

		List<Book> books = new ArrayList<>(IterableUtil.makeList(bookRepository
				.findAll()));

		BookComparator bookComparator = new BookComparator(books, votes);

		return bookComparator.getFirstTied();
	}

	public boolean isFinished(List<Book> books, VoteSession session) {
		List<Vote> votes = session.getVotes();

		return isFinished(books, votes);
	}

	public boolean isFinished(VoteSession session) {
		List<Vote> votes = session.getVotes();
		List<Book> books = IterableUtil.makeList(bookRepository.findAll());
		return isFinished(books, votes);
	}

	
	public boolean handleFinished(VoteSession session) {
		if (isFinished(session)) {
			saveSessionResult(session);
			return true;
		}
		return false;
	}

	private void saveSessionResult(VoteSession session) {
		List<Book> sortedBooks = sort(session.getVotes());
		for (int i = 0; i < sortedBooks.size(); i++) {
			voteSessionResultRepository.save(new VoteSessionResult(session,	sortedBooks.get(i), i));
		}
	}

}
