package com.kodilla.ecommercee.domain;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTS")
@Data
public class Product {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @NotNull
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "PRICE")
    private BigDecimal price;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @ManyToMany(cascade = CascadeType.MERGE, mappedBy = "productList")
    private List<Cart> cartList = new ArrayList<>();

    public Product( Group group, String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.group = group;
    }

    public Product(Long id, String name, BigDecimal price, Group group) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.group = group;
    }
}
