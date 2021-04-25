package com.example.server.Uploads;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String url;

    @Column(unique = true)
    private UUID slug;

    public FileModel(String name, String url, Integer id) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public UUID getSlug() {
        return slug;
    }

    public void setSlug(UUID slug) {
        this.slug = slug;
    }

    public FileModel() {
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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
