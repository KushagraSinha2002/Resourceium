package com.bitlegion.server.uploads;

import java.nio.file.Paths;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import com.bitlegion.server.accounts.Account;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfUpload;

    private String name;

    @Column(unique = true)
    private String slug;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public Account getAccount() {
        return this.account;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateOfUpload() {
        return this.dateOfUpload;
    }

    public void setDateOfUpload(Date dateOfUpload) {
        this.dateOfUpload = dateOfUpload;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setAccount(Account user) {
        this.account = user;
    }

    public File(String name, String url, Integer id) {
        this.id = id;
        this.name = name;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public File() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileLocation() {
        return Paths.get("user--" + this.getAccount().getId().toString(), this.slug).toString();
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", slug='" + getSlug() + "'" + ", user='"
                + getAccount().getUsername() + "'" + "}";
    }
}
