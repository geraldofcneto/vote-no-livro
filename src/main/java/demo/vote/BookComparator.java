package demo.vote;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import demo.book.Book;

public class BookComparator implements Comparator<Book> {

	private List<Vote> votes;
	private List<Book> books;

	public BookComparator(List<Vote> votes) {
		this.votes = votes;
		this.books = inferBooks(votes);
	}

	public BookComparator(List<Book> books, List<Vote> votes) {
		this.books = books;
		this.votes = votes;
	}

	private List<Book> inferBooks(List<Vote> votes) {
		Set<Book> books = new HashSet<>();
		for (Vote vote : votes) {
			books.add(vote.getWinner());
			books.add(vote.getLoser());
		}
		return new ArrayList<>(books);
	}

	@Override
	public int compare(Book book1, Book book2) {
		if (win(book1, book2))
			return -1;
		if (win(book2, book1))
			return 1;

		return 0;

	}

	public Set<Book> getTied(){
		Set<Book> tied = new HashSet<>();
		
		for (int i = 0; i < books.size(); i++)
			for (int j = i + 1; j < books.size(); j++)
				if (tie(books.get(i), books.get(j))){
					tied.add(books.get(i));
					tied.add(books.get(j));
				}
		
		return tied;
	}
	
	public boolean isTied() {
		return !getTied().isEmpty();
	}

	protected boolean win(Book winner, Book loser) {
		return directWin(winner, loser, votes)
				|| indirectWin(winner, loser, votes);
	}

	protected boolean tie(Book b1, Book b2) {
		return !win(b1, b2) && !win(b2, b1);
	}

	protected boolean directWin(Book winner, Book loser, List<Vote> votes) {
		for (Vote vote : votes)
			if (vote.getWinner().equals(winner)
					&& vote.getLoser().equals(loser))
				return true;

		return false;
	}

	protected boolean indirectWin(Book winner, Book loser, List<Vote> votes) {
		for (Vote vote : votes)
			if (vote.getWinner().equals(winner)) {
				Book intermediate = vote.getLoser();
				if (directWin(winner, intermediate, votes)
						&& win(intermediate, loser))
					return true;
			}

		return false;
	}

}
