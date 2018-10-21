package hu.bme.szarch.ibdb.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String email;

    private String password;

    private OffsetDateTime dateOfBirth;

    @OneToMany
    private List<Favourite> favourites;

    @OneToMany
    private List<Category> categories;

    @OneToMany
    private List<Review> reviews;

    @Enumerated(EnumType.STRING)
    private Roles role;

}
