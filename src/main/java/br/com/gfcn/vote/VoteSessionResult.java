package br.com.gfcn.vote;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.gfcn.book.Book;

@Entity
public class VoteSessionResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne
	private VoteSession session;
	
	@OneToMany
	private List<Book> sortedBooks;
	
	private int position;

	protected VoteSessionResult() {
	}
	
	public VoteSessionResult(VoteSession session, List<Book> sortedBooks, int position) {
		this.session = session;
		this.sortedBooks = sortedBooks;
		this.position = position;
	}

	public VoteSession getSession() {
		return session;
	}

	public void setSession(VoteSession session) {
		this.session = session;
	}

	public List<Book> getSortedBooks() {
		return sortedBooks;
	}

	public void setSortedBooks(List<Book> sortedBooks) {
		this.sortedBooks = sortedBooks;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "VoteSessionResult [id=" + id + ", session=" + session
				+ ", sortedBooks=" + sortedBooks + ", position=" + position
				+ "]";
	}

}
