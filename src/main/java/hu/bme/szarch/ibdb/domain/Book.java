package hu.bme.szarch.ibdb.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.userId.UUIDGenerator")
    private String id;
    
    private String title;

    private String author;

    private OffsetDateTime published;

    private String publisher;

    @OneToMany
    private List<Review> reviews;

    @OneToMany
    private List<Category> categories;

    private String imageUrl;

    private String summary;

    private int pageNumber;

    private int sold;

    private int views;

}
