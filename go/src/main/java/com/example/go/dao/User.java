package com.example.go.dao;

import javax.persistence.*;

@Entity
public class User {
    @Id
    private Long id;

    @Column(nullable = false)
    private String password;

    public User() {}

    public User(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
