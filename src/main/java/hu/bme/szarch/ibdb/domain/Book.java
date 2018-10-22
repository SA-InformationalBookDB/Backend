package hu.bme.szarch.ibdb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    
    private String title;

    private String author;

    private OffsetDateTime published;

    private String publisher;

    @OneToMany
    private List<Review> reviews;

    private String imageUrl;

    private String summary;

    private int pageNumber;

    private int sold;

    private int views;

}
