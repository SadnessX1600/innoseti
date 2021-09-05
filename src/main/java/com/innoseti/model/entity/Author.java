package com.innoseti.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Entity
@Data
public class Author {

    @GeneratedValue
    @Id
    @Column(name = "author_id")
    private long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String name;

    @Column
    @ManyToMany(cascade = {CascadeType.ALL})
    private List<Book> book;
}
