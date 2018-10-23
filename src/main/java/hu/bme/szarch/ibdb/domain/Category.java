package hu.bme.szarch.ibdb.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Category {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    private String id;

    private String name;

}
