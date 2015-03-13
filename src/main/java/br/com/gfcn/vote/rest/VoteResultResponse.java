package br.com.gfcn.vote.rest;

import br.com.gfcn.vote.VoteSession;
import br.com.gfcn.vote.VoteSessionResult;

public class VoteResultResponse extends Response {

	VoteSessionResult result;

	public VoteResultResponse(VoteSession session) {
		super(session);
		result = session.getResult();
	}
	
	public VoteSessionResult getResult() {
		return result;
	}
	
	public void setResult(VoteSessionResult result) {
		this.result = result;
	}

}
