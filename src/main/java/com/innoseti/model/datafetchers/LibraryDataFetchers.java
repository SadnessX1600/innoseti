package com.innoseti.model.datafetchers;

import com.innoseti.model.entity.Author;
import com.innoseti.model.entity.Book;
import com.innoseti.service.LibraryService;
import graphql.schema.DataFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class LibraryDataFetchers {
    private static final Logger LOGGER = LoggerFactory.getLogger(LibraryDataFetchers.class);

    private final LibraryService libraryService;

    @Autowired
    public LibraryDataFetchers(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    public DataFetcher<List<Book>> getBooksByAuthor() {
        return dataFetchingEnvironment -> {
            Map<String, Object> authorMap = dataFetchingEnvironment.getArgument("author");
            final String name = authorMap.get("name") != null ? authorMap.get("name").toString() : null;
            return libraryService.getBooksByAuthor(name);
        };
    }

    public DataFetcher<List<Book>> getAllBooks() {
        return dataFetchingEnvironment -> libraryService.getAllBooks();
    }

    public DataFetcher<Author> getAuthorByName() {
        return dataFetchingEnvironment -> {
            final String name = dataFetchingEnvironment.getArgument("name");
            return libraryService.getAuthor(name);
        };
    }

    public DataFetcher<Author> saveAuthor() {
        return dataFetchingEnvironment -> {
            final String name = dataFetchingEnvironment.getArgument("name");
            final List<Map<String, Object>> bookMapList = dataFetchingEnvironment.getArgument("book");
            List<Book> books = new ArrayList<>();
            for (Map<String, Object> bookMap :
                    bookMapList) {
                Book book = new Book();
                book.setTitle(bookMap.get("title") != null ? bookMap.get("title").toString() : null);
                books.add(book);
            }

            return libraryService.saveAuthor(name, books);
        };
    }

    public DataFetcher<Book> saveBook() {
        return dataFetchingEnvironment -> {
            final String title = dataFetchingEnvironment.getArgument("title");
            final List<Map<String, Object>> authorsMapList = dataFetchingEnvironment.getArgument("authors");
            List<Author> authors = new ArrayList<>();
            for (Map<String, Object> authorsMap :
                    authorsMapList) {
                Author author = new Author();
                author.setName(authorsMap.get("name") != null ? authorsMap.get("name").toString() : null);
                authors.add(author);
            }
            return libraryService.saveBook(title, authors);
        };
    }

}

