package com.kodilla.ecommercee.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
@Data
public class User {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @NotNull
    @Column(name = "USER_ID", unique = true)
    private Long userId;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @Column(name = "IS_ACTIVE")
    private boolean isActive = false;

    @Column(name = "KEY1")
    private LocalDateTime expiryDate = null;

    @Column(name = "KEY2")
    private int key = -1;

    @OneToMany(
            targetEntity = Cart.class,
            mappedBy = "user",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    private List<Cart> carts = new ArrayList<>();

    public User (String name) {
        this.name=name;
    }


}