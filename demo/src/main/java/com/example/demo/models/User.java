package com.example.demo.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="users")
public class User {

    public static enum Provider {
        GOOGLE,LOCAL;
    };

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="email", nullable = false,unique = true)
    private String email;

    @Column(name="hashedPassword")
    private String hashedPassword;

    @Column(name = "salt")
    private String salt;

    @Column(name="gender", nullable = false)
    private String gender;

    @Column(name="googleId")
    private String googleId;

    @Column(name="createdAt")
    private Date createdAt = new Date();

    @Column(name="updatedAt")
    private Date updatedAt = new Date();

    @Column(name="active")
    private boolean active = false;

    @Column(name = "picture")
    private String picture;

    @Column(name = "provider")
    private Provider provider;

}
