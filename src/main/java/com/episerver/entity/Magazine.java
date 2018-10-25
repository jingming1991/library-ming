package com.episerver.entity;


import java.time.LocalDate;

public class Magazine extends Book {

    private long id;
    private LocalDate publishDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
}
