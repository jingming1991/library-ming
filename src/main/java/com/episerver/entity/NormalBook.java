package com.episerver.entity;


import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class NormalBook extends Book {

    private String summary;

    public NormalBook() {
        setAuthorIds(new ArrayList<>());
        setAuthorMails(new ArrayList<>());
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }
}
