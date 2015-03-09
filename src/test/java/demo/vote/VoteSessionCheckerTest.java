package demo.vote;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import demo.book.Book;

@Ignore
public class VoteSessionCheckerTest {

	VoteSessionChecker checker = new VoteSessionChecker();
	
	private List<Vote> votes;
	private List<Book> books;
	
	@Before
	public void setUp() {
		
		books = Arrays.asList(new Book("title 0", "author 0"),
				new Book("title 1", "author 1"),
				new Book("title 2", "author 2"),
				new Book("title 3", "author 3"),
				new Book("title 4", "author 4"));
		
		VoteSession voteSession = new VoteSession();
		
		votes = Arrays.asList(new Vote(books.get(0), books.get(1), voteSession),
				new Vote(books.get(2), books.get(3), voteSession),
				new Vote(books.get(0), books.get(2), voteSession),
				new Vote(books.get(1), books.get(3), voteSession),
				new Vote(books.get(3), books.get(4), voteSession));
	}
	
	@Test
	public void testSort() {
		List<Book> proof = books.subList(0, 2);
		
		List<Vote> partialVotes = votes.subList(0, 1);
		
		List<Book> sorted = checker.sort(partialVotes);
		
		assertArrayEquals(proof.toArray(), sorted.toArray());
	}

	@Test(expected=VoteSessionNotFinishedException.class)
	public void testSort2Votes4Books() {
		checker.sort(votes.subList(0, 2));
	}

	@Test(expected=VoteSessionNotFinishedException.class)
	public void testSort3Votes4Books() {
		checker.sort(votes.subList(0, 4));
	}

	@Test
	public void testSort4Votes4Books() {
		List<Book> proof = Arrays.asList(
				books.get(0), 
				books.get(2), 
				books.get(1), 
				books.get(3) );

		List<Vote> partialVotes = votes.subList(0, 4);

		List<Book> sorted = checker.sort(partialVotes);

		assertArrayEquals(proof.toArray(), sorted.toArray());
	}

}