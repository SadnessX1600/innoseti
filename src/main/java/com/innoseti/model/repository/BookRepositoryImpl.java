package com.innoseti.model.repository;

import com.innoseti.model.entity.Author;
import com.innoseti.model.entity.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("from Book", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> findByAuthorName(String name) {
        TypedQuery<Book> query = em.createQuery("select b from Book b inner join b.authors a where a.name = :author_name", Book.class);
        query.setParameter("author_name", name);
        return query.getResultList();
    }

    @Override
    public Book saveBook(String title, List<Author> authors) throws SQLException {
        TypedQuery<Author> query = em.createQuery("select a from Author a left join a.book where a.name in :author_names", Author.class);
        query.setParameter("author_names", authors.isEmpty() ? List.of("Unknown") : authors.stream().map(Author::getName).collect(Collectors.toList()));
        Book bookToSave = new Book();
        bookToSave.setTitle(title);
        List<Author> persistedAuthors = query.getResultList();
        if (persistedAuthors == null || persistedAuthors.isEmpty()) {
            throw new SQLException("Book can't be saved without an author");
        }
        bookToSave.setAuthors(persistedAuthors);
        persistedAuthors.forEach(author -> author.getBook().add(bookToSave));
        persistedAuthors.forEach(author -> em.persist(author));
        return bookToSave;
    }

}
