package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.domain.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


// CommandLineRunner interface geimplementeerd: Spring zoekt instances hiervan en runt ze
// @Component: Spring-managed component
@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    // Wanneer Spring deze Component in de context brengt zal het via DI instances van de repositories meegeven
    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Publisher wn = new Publisher("Wolters", "Blaatlaan 15", "9743 XE", "Groningen");
        publisherRepository.save(wn);

        Author eric = new Author("Eric", "Evans");
        Book ddd = new Book("Domain Driven Design", "123123");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);
        ddd.setPublisher(wn);
        wn.getBooks().add(ddd);

        // opslag nu in een in-mem H2 database
        authorRepository.save(eric);
        bookRepository.save(ddd);
        publisherRepository.save(wn);

        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development", "37823989");
        noEJB.setPublisher(wn);
        wn.getBooks().add(noEJB);

        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);

        authorRepository.save(rod);
        bookRepository.save(noEJB);
        publisherRepository.save(wn);

        System.out.println("Started in Bootstrap");
        System.out.println("Number of books: " + bookRepository.count());
        System.out.println("Publisher number of books: " + wn.getBooks().size());
    }
}
