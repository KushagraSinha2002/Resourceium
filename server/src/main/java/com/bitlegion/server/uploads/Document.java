package com.bitlegion.server.uploads;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date dateOfUpload;

    @Column(nullable = false, unique = true)
    private Long storageID;

    private String name;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "folder_id", referencedColumnName = "id")
    private Folder folder;

    public String getDescription() {
        return description;
    }

    public Date getDateOfUpload() {
        return this.dateOfUpload;
    }

    public Long getStorageID() {
        return this.storageID;
    }

    public void setStorageID(Long storageID) {
        this.storageID = storageID;
    }

    public void setDateOfUpload(Date dateOfUpload) {
        this.dateOfUpload = dateOfUpload;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Document(String name, String url, Integer id) {
        this.id = id;
        this.name = name;
    }

    public Document() {
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

    public String getUrl() {
        return "files/download/" + this.getStorageID();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Folder getFolder() {
        return this.folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", description='" + getDescription() + "'" + ", dateOfUpload='"
                + getDateOfUpload() + "'" + ", storageID='" + getStorageID() + "'" + ", name='" + getName() + "'"
                + ", folder='" + getFolder().getTitle() + "'" + "}";
    }

}
