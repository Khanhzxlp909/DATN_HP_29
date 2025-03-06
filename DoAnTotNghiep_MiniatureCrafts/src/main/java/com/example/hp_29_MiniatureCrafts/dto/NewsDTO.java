package com.example.hp_29_MiniatureCrafts.dto;

public class NewsDTO {
    private Integer id;
    private String title;
    private String content;
    private String img;

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

    public NewsDTO() {
    }

    public NewsDTO(Integer id, String title, String content, String img) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.img = img;
    }
}
