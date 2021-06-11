package com.bitlegion.server.accounts;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bitlegion.server.discussions.Discussion;
import com.bitlegion.server.socials.Favorite;
import com.bitlegion.server.uploads.Folder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstName;

    private String lastName;

    @Column(unique = true, updatable = false)
    private String username;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(unique = true, updatable = false)
    private String email;

    private String bio;

    private String country;

    @Column(nullable = false)
    private String password;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "discussion_account", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "discussion_id"))
    private Set<Discussion> discussions = new HashSet<Discussion>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private Set<Folder> folders = new HashSet<Folder>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private Set<Favorite> favorites = new HashSet<Favorite>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "account")
    private Token token;

    public void setPassword(String password) {
        try {
            this.password = PasswordHash.createHash(password).toString();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyPassword(String password) {
        try {
            return PasswordHash.validatePassword(password, this.getPassword());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addDiscussion(Discussion discussion) {
        this.discussions.add(discussion);
        System.out.println(this.discussions);
    }

    public void addFolder(Folder folder) {
        this.folders.add(folder);
    }

    public void addFavorite(Favorite favorite) {
        this.favorites.add(favorite);
    }

    public String getImageURL() {
        return "https://picsum.photos/seed/" + this.getId() + "/300";
    }

    public Integer getDocumentCount() {
        int sum = 0;
        Collection<Folder> folders = this.getFolders();
        for (Folder f : folders) {
            sum = sum + f.getDocuments().size();
        }
        return sum;
    }

}
