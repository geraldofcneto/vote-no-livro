package demo.book;

import static org.junit.Assert.*;

import org.junit.Test;

public class BookTest {

	@Test
	public void createBook() {
		Book book = new Book("Le Comte de Monte-Cristo", "Alexandre Dumas");
		assertEquals("Book created", "Book [id=0, title=Le Comte de Monte-Cristo, author=Alexandre Dumas]", book.toString());
	}

}
