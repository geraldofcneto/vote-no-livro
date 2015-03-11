package demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import demo.book.Book;
import demo.book.BookRepository;
import demo.vote.Vote;
import demo.vote.VoteRepository;
import demo.vote.VoteSession;
import demo.vote.VoteSessionRepository;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    VoteRepository voteRepository;
    
    @Autowired
    VoteSessionRepository voteSessionRepository;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... arg0) throws Exception {
		Book b1 = bookRepository.save(new Book("Le Comte de Monte-Cristo", "Alexandre Dumas, pére"));
		Book b2 = bookRepository.save(new Book("The Da Vinci Code", "Dan Brown"));
		bookRepository.save(new Book("Le Petit Prince", "Antoine de Saint-Exupéry"));
		bookRepository.save(new Book("Het Achterhuis", "Anne Frank"));
		bookRepository.save(new Book("The Perks of Being a Wallflower", "Stephen Chbosky"));
    

		VoteSession voteSession = voteSessionRepository.save(new VoteSession());
		
		voteRepository.save(new Vote(b1, b2, voteSession));
		
		System.out.println("Books found with findAll():");
		System.out.println("-------------------------------");
    	
		for (Book book : bookRepository.findAll()) {
            System.out.println(book);
        }
		
		for (VoteSession vs : voteSessionRepository.findAll()) {
            System.out.println(vs);
        }
		
		List<Book> a = null;
		
		System.out.println(a);

		for (Vote v : voteRepository.findAll()) {
            System.out.println(v);
        }
	}
    
    
}
