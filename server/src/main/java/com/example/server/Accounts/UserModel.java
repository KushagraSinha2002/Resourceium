package com.example.server.Accounts;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.server.Uploads.FileModel;

@Entity // This tells Hibernate to make a table out of this class
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    private String bio;

    @OneToMany
    private Collection<FileModel> fileModels = new ArrayList<FileModel>();

    public Collection<FileModel> getFileModels() {
        return this.fileModels;
    }

    public void setFileModels(Collection<FileModel> fileModels) {
        this.fileModels = fileModels;
    }

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

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", email='" + getEmail() + "'" + ", bio='"
                + getBio() + "'" + ", fileModels='" + getFileModels() + "'" + "}";
    }

}
