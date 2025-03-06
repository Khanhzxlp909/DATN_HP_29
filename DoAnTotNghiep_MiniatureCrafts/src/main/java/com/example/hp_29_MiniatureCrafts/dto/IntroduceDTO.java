package com.example.hp_29_MiniatureCrafts.dto;

public class IntroduceDTO {
    private Integer id;
    private String header;
    private String title;
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public IntroduceDTO(Integer id, String header, String title, String content) {
        this.id = id;
        this.header = header;
        this.title = title;
        this.content = content;
    }

    public IntroduceDTO() {
    }
}
