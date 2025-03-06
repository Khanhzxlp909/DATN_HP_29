package com.example.hp_29_MiniatureCrafts.entity;

import jakarta.persistence.*;

@Entity
public class introduce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String header;
    private String title;
    private String content;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
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

    public introduce(Integer id, String header, String title, String content) {
        this.Id = id;
        this.header = header;
        this.title = title;
        this.content = content;
    }

    public introduce() {
    }
}
