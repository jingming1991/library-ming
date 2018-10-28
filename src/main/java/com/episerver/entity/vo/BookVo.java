package com.episerver.entity.vo;

public class BookVo {

    private String title;

    private String numberISBN;
    private String authorName;
    private String email;
    private String publishDate;
    private String summary;
    private BookeType bookeType;

    //Hidden
    private String authorId;
    private String bookId;

    public BookVo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumberISBN() {
        return numberISBN;
    }

    public void setNumberISBN(String numberISBN) {
        this.numberISBN = numberISBN;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public BookeType getBookeType() {
        return bookeType;
    }

    public void setBookeType(BookeType bookeType) {
        this.bookeType = bookeType;
    }
}
