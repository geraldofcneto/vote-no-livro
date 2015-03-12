package br.com.gfcn.vote.rest;

import java.util.Set;

import br.com.gfcn.book.Book;
import br.com.gfcn.vote.VoteSession;

public class NomineesResponse {

	VoteSession session;
	Set<Book> nominees;
	Boolean finished;

	public NomineesResponse(VoteSession session, Set<Book> nominees, Boolean finished) {
		this.session = session;
		this.nominees = nominees;
		this.finished = finished;
	}

	public NomineesResponse(VoteSession session, Set<Book> nominees) {
		this.session = session;
		this.nominees = nominees;
		this.finished = nominees.isEmpty();
	}

	public VoteSession getSession() {
		return session;
	}

	public void setSession(VoteSession session) {
		this.session = session;
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
