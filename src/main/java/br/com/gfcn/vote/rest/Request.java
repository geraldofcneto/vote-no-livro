package br.com.gfcn.vote.rest;

import br.com.gfcn.book.Book;
import br.com.gfcn.vote.VoteSession;

public class Request {

	VoteSession session;
	Book winner;
	Book loser;

	public VoteSession getSession() {
		return session;
	}
	public void setSession(VoteSession session) {
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
	@Override
	public String toString() {
		return "Request [session=" + session + ", winner=" + winner
				+ ", loser=" + loser + "]";
	}
		
}
