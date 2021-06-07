package com.bitlegion.server.socials;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.CascadeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;

import com.bitlegion.server.uploads.Folder;
import com.bitlegion.server.discussions.Discussion;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date dateOfUpload;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "post")
    private Folder folder;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "discussion_id", nullable = false)
    private Discussion discussion;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateOfUpload() {
        return this.dateOfUpload;
    }

    public void setDateOfUpload(Date dateOfUpload) {
        this.dateOfUpload = dateOfUpload;
    }

    public Folder getFolder() {
        return this.folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Discussion getDiscussion() {
        return this.discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", dateOfUpload='" + getDateOfUpload() + "'" + ", folder='" + getFolder()
                + "'" + ", discussion='" + getDiscussion() + "'" + "}";
    }

}
