package com.phoenix.data.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    private boolean enabled;

    @ElementCollection
    private List<Authority> authorities;

    @Column(length = 500)
    private String address;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @OneToOne(cascade = CascadeType.PERSIST)
    private final Cart myCart;

    public AppUser(){

        this.myCart = new Cart();
        this.myCart.setTotalPrice(0.0);
    }
}
