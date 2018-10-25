package com.episerver.entity;


import org.springframework.data.annotation.Transient;

import java.util.List;

public class Book {


    private String title;
    private String numberISBN;
    private List<Long> autorIds;
    @Transient
    private List<String> autorMails;

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

    public List<Long> getAutorIds() {
        return autorIds;
    }

    public void setAutorIds(List<Long> autorIds) {
        this.autorIds = autorIds;
    }


    public List<String> getAutorMails() {
        return autorMails;
    }

    public void setAutorMails(List<String> autorMails) {
        this.autorMails = autorMails;
    }
}
