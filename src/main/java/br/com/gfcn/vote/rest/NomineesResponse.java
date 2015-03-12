package br.com.gfcn.vote.rest;

import java.util.Set;

import br.com.gfcn.book.Book;
import br.com.gfcn.vote.VoteSession;

public class NomineesResponse extends Response {

	Set<Book> nominees;
	
	public NomineesResponse(VoteSession session, Set<Book> nominees, Boolean finished) {
		super(session);
		this.nominees = nominees;
		this.finished = finished;
	}

	public NomineesResponse(VoteSession session, Set<Book> nominees) {
		super(session);
		this.nominees = nominees;
		this.finished = nominees.isEmpty();
	}

	public Set<Book> getNominees() {
		return nominees;
	}

	public void setNominees(Set<Book> nominees) {
		this.nominees = nominees;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	@Override
	public String toString() {
		return "Response [session=" + session + ", nominees=" + nominees
				+ ", finished=" + finished + "]";
	}


}
