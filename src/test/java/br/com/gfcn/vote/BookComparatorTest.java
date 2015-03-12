package br.com.gfcn.vote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.gfcn.book.Book;
import br.com.gfcn.vote.BookComparator;
import br.com.gfcn.vote.Vote;
import br.com.gfcn.vote.VoteSession;

public class BookComparatorTest {

	BookComparator finishedComparator;
	BookComparator incompleteComparator;
	private List<Book> books;
	private List<Vote> votes;
	
	@Before
	public void populate(){
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
				new Vote(books.get(3), books.get(4), voteSession),
				new Vote(books.get(1), books.get(2), voteSession));
		
		finishedComparator = new BookComparator(votes);
		incompleteComparator = new BookComparator(votes.subList(0, 4));
	}
	
	@Test
	public void testTieEmptyVotes(){
		Assert.assertTrue(new BookComparator(books, new ArrayList<Vote>()).isTied());
	}

	@Test
	public void testTieWithVotes(){
		Assert.assertTrue(incompleteComparator.tie(books.get(0), books.get(4)));
	}

	@Test
	public void testNotTie(){
		Assert.assertFalse(finishedComparator.isTied());
	}

	@Test
	public void testTie(){
		Assert.assertFalse(incompleteComparator.win(books.get(0), books.get(4)));
		Assert.assertFalse(incompleteComparator.win(books.get(4), books.get(0)));
		Assert.assertTrue(incompleteComparator.tie(books.get(0), books.get(4)));
	}

	
	@Test
	public void testWin(){
		Assert.assertTrue(finishedComparator.win(books.get(0), books.get(1)));
	}

	@Test
	public void testIndirectWin(){
		Assert.assertTrue(finishedComparator.win(books.get(0), books.get(3)));
	}

	@Test
	public void testDoubleIndirectWin(){
		Assert.assertTrue(finishedComparator.win(books.get(0), books.get(4)));
	}

	@Test
	public void testTiedSet(){
		Assert.assertEquals(new HashSet<>(books), new BookComparator(books, new ArrayList<Vote>()).getTied());
	}

	@Test
	public void testFirstTiedSet(){
		Assert.assertEquals(new HashSet<>(books.subList(0, 2)), new BookComparator(books, new ArrayList<Vote>()).getFirstTied());
	}

}
