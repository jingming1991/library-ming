package com.episerver.entity;


import java.util.List;

public class Book {
    private String title;
    private String numberISBN;
    private List<Long> autorIds;

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
}
