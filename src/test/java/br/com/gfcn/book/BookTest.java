package br.com.gfcn.book;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.gfcn.book.Book;

public class BookTest {

	@Test
	public void createBook() {
		Book book = new Book("Le Comte de Monte-Cristo", "Alexandre Dumas");
		assertEquals("Book created", "Book [id=null, title=Le Comte de Monte-Cristo, author=Alexandre Dumas]", book.toString());
	}

}
