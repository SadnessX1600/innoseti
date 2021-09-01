package com.innoseti.service;


import com.innoseti.model.entity.Author;
import com.innoseti.model.entity.Book;
import com.innoseti.model.repository.AuthorRepository;
import com.innoseti.model.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LibraryService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public LibraryService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getBooksByAuthor(String name) {
        return bookRepository.findByAuthorName(name);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Author getAuthor(String name) {
        return authorRepository.findByName(name);
    }

    public Author saveAuthor(String name, List<Book> books) {
        return authorRepository.saveAuthor(name, books);
    }

    public Book saveBook(String title, List<Author> authors) {
        return bookRepository.saveBook(title, authors);
    }
}
