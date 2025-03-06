package com.example.hp_29_MiniatureCrafts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public News() {
    }

    public News(Integer id, String title, String content, String img) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.img = img;
    }

    private String img;
}
