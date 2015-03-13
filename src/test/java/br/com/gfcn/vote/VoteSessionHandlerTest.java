package br.com.gfcn.vote;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.gfcn.book.Book;

public class VoteSessionHandlerTest {

	VoteSessionHandler checker = new VoteSessionHandler();

	private List<Vote> votes;
	private List<Book> books;

	private VoteSession voteSession;

	@Before
	public void setUp() {

		books = Arrays.asList(new Book("title 0", "author 0"), new Book(
				"title 1", "author 1"), new Book("title 2", "author 2"),
				new Book("title 3", "author 3"),
				new Book("title 4", "author 4"));

		voteSession = new VoteSession();

		votes = Arrays.asList(
				new Vote(books.get(0), books.get(1), voteSession), new Vote(
						books.get(2), books.get(3), voteSession), new Vote(
						books.get(0), books.get(2), voteSession), new Vote(
						books.get(1), books.get(3), voteSession), new Vote(
						books.get(3), books.get(4), voteSession), new Vote(
						books.get(1), books.get(2), voteSession));
	}

	@Test
	public void testSort() {
		List<Book> proof = books.subList(0, 2);

		List<Vote> partialVotes = votes.subList(0, 1);

		List<Book> sorted = checker.sort(partialVotes);

		assertArrayEquals(proof.toArray(), sorted.toArray());
	}

	@Test
	public void testSortCompleted() {
		List<Book> sorted = checker.sort(votes);
		assertArrayEquals(books.toArray(), sorted.toArray());
	}

	@Test
	public void testIsFinished() {
		Assert.assertTrue(checker.isFinished(books, votes));
	}

	@Test
	public void testSessionIsFinished() {
		List<Vote> v = new ArrayList<>();
		v.add(vote(0, 1));
		v.add(vote(0, 2));
		v.add(vote(0, 3));
		v.add(vote(0, 4));
		v.add(vote(1, 2));
		v.add(vote(1, 3));
		v.add(vote(1, 4));
		v.add(vote(2, 3));
		v.add(vote(2, 4));
		v.add(vote(3, 4));
		Assert.assertTrue(checker.isFinished(books, v));
	}

	private Vote vote(int win, int lose) {
		return new Vote(books.get(win), books.get(lose), null);
	}

	@Test
	public void testNotFinished() {
		Assert.assertFalse(checker.isFinished(books, votes.subList(0, 4)));
	}

}
