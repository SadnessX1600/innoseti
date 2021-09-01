package com.innoseti.model.repository;

import com.innoseti.model.entity.Author;
import com.innoseti.model.entity.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


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
    public Book saveBook(String title, List<Author> authors) {
        Book bookToSave = new Book();
        bookToSave.setTitle(title);
        bookToSave.setAuthors(authors);
        em.persist(bookToSave);
        return bookToSave;
    }

}
