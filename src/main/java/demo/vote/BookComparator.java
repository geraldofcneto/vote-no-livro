package demo.vote;

import java.util.Comparator;
import java.util.List;

import demo.book.Book;

public class BookComparator implements Comparator<Book>{
	
	private List<Vote> votes;
	private List<Book> books;

	public BookComparator(List<Vote> votes) {
		this.votes = votes;
	}
	
	public BookComparator(List<Book> books, List<Vote> votes) {
		this.books = books;
		this.votes = votes;
	}
	
	@Override
	public int compare(Book book1, Book book2) {
		if (win(book1, book2))
			return -1;
		if (lose(book1, book2))
			return 1;
		
		return 0;
		
	}

	public boolean isTied(){
		for (int i = 0; i < books.size() ; i++)
			for (int j = i+1 ; j < books.size(); j++)
				if (tie(books.get(i), books.get(j)))
					return true;
		
		return false;
	}
	
	// Recursivo
	public boolean win(Book winner, Book loser) {
		return directWin(winner, loser, votes) || indirectWin(winner, loser, votes);
	}

	public boolean lose (Book loser, Book winner){
		return directWin(loser, winner, votes) || indirectWin(loser, winner, votes);
	}
	
	public boolean tie(Book b1, Book b2){
		return !win(b1, b2) && !lose(b1, b2);
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
						&& win(intermediate, loser))
					return true;
			}

		return false;
	}

	
}
