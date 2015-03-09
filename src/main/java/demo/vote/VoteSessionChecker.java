package demo.vote;

import java.util.ArrayList;
import java.util.List;

import demo.book.Book;

public class VoteSessionChecker {

	public boolean isFinished(List<Book> books, List<Vote> votes) {
		for (Book book : books) {
			for (Vote vote : votes) {
				if (vote.isNominee(book)){
					break;
				}

				return false;
			}
		}
		return true;
	}

	public List<Book> sort(List<Vote> votes){
		List<Book> list = new ArrayList<Book>();
		
		for (Vote vote : votes) {
			insert(list, vote);
		}
		
		return list;
	}

	private void insert(List<Book> list, Vote vote) {
		list.add(vote.getWinner());
		list.add(vote.getLoser());
	}
	
}
