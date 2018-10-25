package com.episerver.entity;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class NormalBook extends Book {
    @Id
    private String id;
    private String summary;

    public NormalBook() {
        this.id = new ObjectId().toString();
    }

    public String getId() {
        return id;
    }


    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }
}
