package com.innoseti.model.repository;

import com.innoseti.model.entity.Author;
import com.innoseti.model.entity.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Author findByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from Author a left join a.book where a.name = :name", Author.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Author saveAuthor(String name, List<Book> books) {
        Author authorToSave = new Author();
        authorToSave.setName(name);
        authorToSave.setBook(books);
        em.persist(authorToSave);
        return authorToSave;
    }
}
