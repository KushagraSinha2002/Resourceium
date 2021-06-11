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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bitlegion.server.accounts.Account;
import com.bitlegion.server.socials.Favorite;
import com.bitlegion.server.socials.Liked;
import com.bitlegion.server.socials.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String description;

    private String title;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date dateOfUpload;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastEdited;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "folder_tags", joinColumns = {
            @JoinColumn(name = "folder_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "tag_id", referencedColumnName = "id", nullable = false) })
    private Set<Tag> tags = new HashSet<Tag>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    // @JsonIgnore
    @OneToMany(mappedBy = "folder")
    private Collection<Document> documents = new ArrayList<Document>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "folder")
    private Collection<Favorite> favorites = new ArrayList<Favorite>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "folder")
    private Collection<Liked> liked = new ArrayList<Liked>();

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public void addDocument(Document document) {
        this.getDocuments().add(document);
    }

    public void removeDocument(Document document) {
        this.getDocuments().remove(document);
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

}
