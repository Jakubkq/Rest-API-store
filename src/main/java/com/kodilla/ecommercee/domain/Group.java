package com.kodilla.ecommercee.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"GROUPS\"")
@Data
public class Group {

    @Id
    @NotNull
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "ID",unique = true)
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @OneToMany(
            targetEntity = Product.class,
            mappedBy = "group",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    private List<Product> products = new ArrayList<>();

    public Group(String name) {

        this.name = name;
    }

    public Group(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
