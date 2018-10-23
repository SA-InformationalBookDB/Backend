package hu.bme.szarch.ibdb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Favourite {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.userId.UUIDGenerator")
    private String id;

    @ManyToOne
    private User user;

}
