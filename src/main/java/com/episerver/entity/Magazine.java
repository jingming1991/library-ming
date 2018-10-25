package com.episerver.entity;


import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;

@Document
public class Magazine extends Book {

    private LocalDate publishDate;

    public Magazine() {
        setAuthorIds(new ArrayList<>());
        setAuthorMails(new ArrayList<>());
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
}
