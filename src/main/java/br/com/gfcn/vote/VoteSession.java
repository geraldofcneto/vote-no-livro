package br.com.gfcn.vote;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class VoteSession {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToMany(mappedBy = "session", fetch=FetchType.EAGER)
	@JsonManagedReference("vote-session")
	private List<Vote> votes = new ArrayList<Vote>();

	@OneToOne
	private VoteSessionResult result;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}

	public VoteSessionResult getResult() {
		return result;
	}

	public void setResult(VoteSessionResult result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "VoteSession [id=" + id + ", votes=" + votes + "]";
	}

}
