package com.library.publiclibrary.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document
public class Resource {
    @Id
    private String id;
    private String title;
    private String type;
    private String thematic;
    private LocalDate loanDate;
    private boolean isLent;
    private boolean isAvailable;

    public Resource() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getThematic() {
        return thematic;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public boolean isLent() {
        return isLent;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setThematic(String thematic) {
        this.thematic = thematic;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public void setLent(boolean lent) {
        isLent = lent;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
