package br.com.gfcn.book;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;
	private String author;

	protected Book() {
	}

	public Book(String title, String author) {
		this.title = title;
		this.author = author;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Book [id=" + getId() + ", title=" + title + ", author="
				+ author + "]";
	}
	
	@Override
	public int hashCode(){
	    return new HashCodeBuilder()
	        .append(id)
	        .append(title)
	        .append(author)
	        .toHashCode();
	}

	@Override
	public boolean equals(final Object obj){
	    if(obj instanceof Book){
	        final Book other = (Book) obj;
	        return new EqualsBuilder()
	            .append(id, other.id)
	            .append(title, other.title)
	            .append(author, other.author)
	            .isEquals();
	    } else{
	        return false;
	    }
	}
}
