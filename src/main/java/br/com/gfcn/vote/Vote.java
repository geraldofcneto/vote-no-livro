package br.com.gfcn.vote;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.gfcn.book.Book;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JoinColumn(name = "win_id")
	private Book winner;
	
	@ManyToOne
	@JoinColumn(name = "lose_id")
	private Book loser;

	@ManyToOne
	@JoinColumn(name = "session_id")
	@JsonBackReference("vote-session")
	private VoteSession session;

	protected Vote() {
	}

	public Vote(Book win, Book lose, VoteSession session) {
		super();
		this.winner = win;
		this.loser = lose;
		this.session = session;
	}

	@Override
	public String toString() {
		return "Vote [id=" + id + ", win=" + winner + ", lose=" + loser
				+ ", session=" + session.getId() + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Book getWinner() {
		return winner;
	}

	public void setWinner(Book winner) {
		this.winner = winner;
	}

	public Book getLoser() {
		return loser;
	}

	public void setLoser(Book loser) {
		this.loser = loser;
	}

	public VoteSession getSession() {
		return session;
	}

	public void setSession(VoteSession session) {
		this.session = session;
	}

	public boolean isNominee(Book book){
		return book.equals(winner) || book.equals(loser);
	}
}
