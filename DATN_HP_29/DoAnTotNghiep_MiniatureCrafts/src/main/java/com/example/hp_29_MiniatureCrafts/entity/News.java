package com.example.hp_29_MiniatureCrafts.entity;

import jakarta.persistence.*;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    @Column(columnDefinition = "NVARCHAR(200)")
    private String summary;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String content;

    private String img;


    public News(Integer id, String title, String summary, String content, String img) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.img = img;
    }


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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String sumary) {
        this.summary = sumary;
    }


}
