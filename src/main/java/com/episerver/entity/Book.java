package com.episerver.entity;


import org.springframework.data.annotation.Transient;

import java.util.List;

public class Book {


    private String title;
    private String numberISBN;
    private List<String> authorIds;
    @Transient
    private List<String> authorMails;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getNumberISBN() {
        return numberISBN;
    }

    public void setNumberISBN(String numberISBN) {
        this.numberISBN = numberISBN == null ? null : numberISBN.trim();
    }

    public List<String> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<String> authorIds) {
        this.authorIds = authorIds;
    }


    public List<String> getAuthorMails() {
        return authorMails;
    }

    public void setAuthorMails(List<String> authorMails) {
        this.authorMails = authorMails;
    }
}
