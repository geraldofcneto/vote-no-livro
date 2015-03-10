package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import demo.book.Book;
import demo.book.BookRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    BookRepository repository;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... arg0) throws Exception {
		repository.save(new Book("Le Comte de Monte-Cristo", "Alexandre Dumas, pére"));
		repository.save(new Book("The Da Vinci Code", "Dan Brown"));
		repository.save(new Book("Le Petit Prince", "Antoine de Saint-Exupéry"));
		repository.save(new Book("Het Achterhuis", "Anne Frank"));
		repository.save(new Book("The Perks of Being a Wallflower", "Stephen Chbosky"));
    	
		System.out.println("Books found with findAll():");
		System.out.println("-------------------------------");
    	
		for (Book book : repository.findAll()) {
            System.out.println(book);
        }
		
		
	}
    
    
}
