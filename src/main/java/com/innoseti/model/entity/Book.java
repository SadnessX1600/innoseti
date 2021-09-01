package com.innoseti.model.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Book {
    @GeneratedValue
    @Id
    @Column(name = "book_id")
    private long id;

    @Column(nullable = false)
    private String title;

    @Column
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "AUTHOR_BOOK", joinColumns = @JoinColumn(name = "book_book_id"), inverseJoinColumns = @JoinColumn(name = "author_author_id"))
    private List<Author> authors;

}
