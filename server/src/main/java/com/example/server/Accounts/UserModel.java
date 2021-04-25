package com.example.server.Accounts;

import java.util.ArrayList;
import java.util.Collection;

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

    private String name;

    private String email;

    private String bio;

    // @OneToMany
    // private Collection<FileModel> fileModel = new ArrayList<FileModel>();

    // public Collection<FileModel> getFileModel() {
    // return this.fileModel;
    // }

    // public void setFileModel(Collection<FileModel> fileModel) {
    // this.fileModel = fileModel;
    // }

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
}
