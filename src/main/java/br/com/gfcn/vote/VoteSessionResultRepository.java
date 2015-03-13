package br.com.gfcn.vote;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.gfcn.book.Book;

public interface VoteSessionResultRepository extends CrudRepository<VoteSessionResult, Long> {
	
	public List<VoteSessionResult> findByBook(Book book);
	
}
