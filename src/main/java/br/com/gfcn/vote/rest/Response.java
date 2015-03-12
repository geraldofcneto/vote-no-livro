package br.com.gfcn.vote.rest;

import br.com.gfcn.vote.VoteSession;

public class Response {

	protected VoteSession session;
	protected Boolean finished;

	public Response(VoteSession session) {
		this.session = session;
	}

	public VoteSession getSession() {
		return session;
	}

	public void setSession(VoteSession session) {
		this.session = session;
	}

}