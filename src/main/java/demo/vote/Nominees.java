package demo.vote;

import demo.book.Book;

public class Nominees {

	private Book nominee1;
	private Book nominee2;
	
	public Nominees(Book nominee1, Book nominee2) {
		this.nominee1 = nominee1;
		this.nominee2 = nominee2;
	}

	public Book getNominee1() {
		return nominee1;
	}

	public void setNominee1(Book nominee1) {
		this.nominee1 = nominee1;
	}

	public Book getNominee2() {
		return nominee2;
	}

	public void setNominee2(Book nominee2) {
		this.nominee2 = nominee2;
	}

	@Override
	public String toString() {
		return "Nominees [nominee1=" + nominee1 + ", nominee2=" + nominee2
				+ "]";
	}
	
}
