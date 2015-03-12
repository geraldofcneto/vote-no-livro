package br.com.gfcn.vote.rest;

import br.com.gfcn.vote.VoteSession;

public class VoteResponse extends Response {

	private static final String VOTE_CREATED = "Vote created";
	String message;
	
	public VoteResponse(VoteSession session) {
		super(session);
		message = VOTE_CREATED;
	}
	
	public VoteResponse(VoteSession session, String message) {
		super(session);
		this.message = message;
	}

}
