package demo.vote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import demo.book.Book;

public class VoteSessionChecker {

	public boolean isFinished(List<Book> books, List<Vote> votes) {
		BookComparator bookComparator = new BookComparator(new ArrayList<>(books), votes);
		
		return !bookComparator.isTied();
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

	
	
	// Recursivo
	public boolean win(Book winner, Book loser, List<Vote> votes) {
		return directWin(winner, loser, votes) || indirectWin(winner, loser, votes);
	}

	public boolean lose (Book loser, Book winner, List<Vote> votes){
		return directWin(loser, winner, votes) || indirectWin(loser, winner, votes);
	}
	
	public boolean tie(Book b1, Book b2, List<Vote> votes){
		return !win(b1, b2, votes) && !lose(b1, b2, votes);
	}
	
	private boolean directWin(Book winner, Book loser, List<Vote> votes) {
		for (Vote vote : votes)
			if (vote.getWinner().equals(winner) && vote.getLoser().equals(loser))
				return true;

		return false;
	}

	private boolean indirectWin(Book winner, Book loser, List<Vote> votes) {
		for (Vote vote : votes)
			if (vote.getWinner().equals(winner)) {
				Book intermediate = vote.getLoser();
				if (directWin(winner, intermediate, votes)
						&& win(intermediate, loser, votes))
					return true;
			}

		return false;
	}


}
