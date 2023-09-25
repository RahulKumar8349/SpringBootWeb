package com.example.web.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",nullable = false)
    @ColumnDefault("'1'")
    private long id;

    @Column(name = "content",nullable = false)
    @ColumnDefault("'My first post'")
    private String content;

    @JsonBackReference
    @ManyToOne
    @ColumnDefault("'1'")
    @JoinColumn(name = "user_id",nullable = false)
    private User user;




    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
