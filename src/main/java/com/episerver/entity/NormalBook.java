package com.episerver.entity;


import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class NormalBook extends Book {

    private String summary;


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }
}
