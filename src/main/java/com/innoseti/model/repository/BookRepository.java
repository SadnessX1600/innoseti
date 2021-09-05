package com.innoseti.model.repository;

import com.innoseti.model.entity.Author;
import com.innoseti.model.entity.Book;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface BookRepository {
    List<Book> findAll();

    List<Book> findByAuthorName(String name);

    Book saveBook(String title, List<Author> authors) throws SQLException;
}
