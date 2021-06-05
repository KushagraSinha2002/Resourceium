package com.bitlegion.server.socials;

import java.util.Date;
import java.util.Collection;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.CascadeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;

import com.bitlegion.server.uploads.Folder;
import com.bitlegion.server.discussions.Discussion;

@Entity
public class Post {

    //private static final String POST = "post";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date dateOfPostGenerated;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "post")
    private Folder folder;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private Collection<Discussion> discussions;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateOfPostGenerated() {
        return this.dateOfPostGenerated;
    }

    public void setDateOfPostGenerated(Date dateOfPostGenerated) {
        this.dateOfPostGenerated = dateOfPostGenerated;
    }

    public Folder getFolder() {
        return this.folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Collection<Discussion> getDiscussions() {
        return this.discussions
;
    }

    public void setDiscussions(Collection<Discussion> discussions) {
        this.discussions = discussions;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", dateOfPostGenerated='" + getDateOfPostGenerated() + "'" +
            ", folder='" + getFolder() + "'" +
            ", discussion='" + getDiscussions() + "'" +
            "}";
    }

}