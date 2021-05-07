package com.bitlegion.server.uploads;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bitlegion.server.accounts.Account;
import com.bitlegion.server.socials.Favorite;
import com.bitlegion.server.socials.Liked;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String description;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateOfUpload;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastEdited;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "folder_tags", joinColumns = { @JoinColumn(name = "folder_id") }, inverseJoinColumns = {
            @JoinColumn(name = "tag_id") })
    private Set<Tag> tags = new HashSet<Tag>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    // @JsonIgnore
    @OneToMany(mappedBy = "folder")
    private Collection<Document> documents = new ArrayList<Document>();

    @JsonIgnore
    @OneToMany(mappedBy = "folder")
    private Collection<Favorite> favorites = new ArrayList<Favorite>();

    @JsonIgnore
    @OneToMany(mappedBy = "folder")
    private Collection<Liked> liked = new ArrayList<Liked>();

    public void setDocument(Document document) {
        this.getDocuments().add(document);
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Collection<Favorite> getFavorites() {
        return this.favorites;
    }

    public void setFavorites(Collection<Favorite> favorites) {
        this.favorites = favorites;
    }

    public Collection<Liked> getLiked() {
        return this.liked;
    }

    public void setLiked(Collection<Liked> liked) {
        this.liked = liked;
    }

    public Integer getId() {
        return this.id;
    }

    public Date getLastEdited() {
        return this.lastEdited;
    }

    public Collection<Document> getDocuments() {
        return this.documents;
    }

    public void setDocuments(Collection<Document> documents) {
        this.documents = documents;
    }

    public void setLastEdited(Date lastEdited) {
        this.lastEdited = lastEdited;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateOfUpload() {
        return this.dateOfUpload;
    }

    public void setDateOfUpload(Date dateOfUpload) {
        this.dateOfUpload = dateOfUpload;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
