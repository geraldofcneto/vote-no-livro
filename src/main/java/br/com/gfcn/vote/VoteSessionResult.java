package br.com.gfcn.vote;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.gfcn.book.Book;

@Entity
public class VoteSessionResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne
	private VoteSession session;
	
	@OneToOne
	private Book book;
	
	private int position;

	protected VoteSessionResult() {
	}
	
	public VoteSessionResult(VoteSession session, Book book, int position) {
		this.session = session;
		this.book = book;
		this.position = position;
	}

	public VoteSession getSession() {
		return session;
	}

	public void setSession(VoteSession session) {
		this.session = session;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "VoteSessionResult [id=" + id + ", session=" + session.getId()
				+ ", book=" + book + ", position=" + position + "]";
	}

	
}
