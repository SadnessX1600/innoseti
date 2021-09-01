package com.innoseti.model.repository;

import com.innoseti.model.entity.Author;
import com.innoseti.model.entity.Book;

import java.util.List;


public interface AuthorRepository {
    Author findByName(String name);

    Author saveAuthor(String name, List<Book> books);

}
