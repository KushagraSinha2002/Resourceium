package com.bitlegion.server.accounts;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.bitlegion.server.uploads.File;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity // This tells Hibernate to make a table out of this class
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    private String bio;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private Collection<File> files;

    public Integer getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<File> getFiles() {
        return this.files;
    }

    public void setFiles(Collection<File> fileModels) {
        this.files = fileModels;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", email='" + getEmail() + "'" + ", bio='"
                + getBio() + "'" + ", fileModels='" + getFiles() + "'" + "}";
    }

}
