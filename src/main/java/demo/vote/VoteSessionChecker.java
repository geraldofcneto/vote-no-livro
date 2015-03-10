package demo.vote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import demo.book.Book;

public class VoteSessionChecker {

	public boolean isFinished(List<Book> books, List<Vote> votes) {
		return !new BookComparator(new ArrayList<>(books), votes).isTied();
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

	public Nominees getTied (){
		return new Nominees(null, null);
	}
}
