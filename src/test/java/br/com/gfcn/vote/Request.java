package br.com.gfcn.vote;

import br.com.gfcn.book.Book;

public class Request {

	private Book winner;
	private Book loser;
	private VoteSession session;

	public Request(Book win, Book lose, VoteSession session) {
		this.winner = win;
		this.loser = lose;
		this.session = session;
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

	@Override
	public String toString() {
		return "Request [winner=" + winner + ", loser=" + loser
				+ ", session=" + session.getId() + "]";
	}

}