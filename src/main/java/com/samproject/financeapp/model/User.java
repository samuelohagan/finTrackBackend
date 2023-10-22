package com.samproject.financeapp.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue
    private Long id;


    private String googleId;
    private String email;
    private String name;
    private String givenName;
    private String familyName;
    private String imageUrl;
    private boolean emailVerified;
}
