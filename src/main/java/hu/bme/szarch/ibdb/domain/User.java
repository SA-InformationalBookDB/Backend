package hu.bme.szarch.ibdb.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity(name = "IbdbUser")
@Getter
@Setter
public class User {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    private String id;

    private String email;

    private String password;

    private OffsetDateTime dateOfBirth;

    @ManyToMany
    private List<Book> favourites;

    @OneToMany
    private List<Category> categories;

    @OneToMany
    private List<Review> reviews;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean enabled = false;

}
