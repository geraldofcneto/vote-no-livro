package br.com.gfcn.book;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

	public Book title(String title);
	
}
