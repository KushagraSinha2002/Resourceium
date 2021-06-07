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

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "discussion_account", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "discussion_id"))
    private Set<Discussion> discussions = new HashSet<Discussion>();

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private Set<Folder> folders = new HashSet<Folder>();

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private Set<Favorite> favorites = new HashSet<Favorite>();

    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "account")
    private Token token;

    public String getPassword() {
        return this.password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Token getToken() {
        return this.token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

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

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Set<Discussion> getDiscussions() {
        return this.discussions;
    }

    public void setDiscussions(Set<Discussion> discussions) {
        this.discussions = discussions;
    }

    public void addDiscussion(Discussion discussion) {
        this.discussions.add(discussion);
    }

    public Collection<Favorite> getFavorites() {
        return this.favorites;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Collection<Folder> getFolders() {
        return this.folders;
    }

    public void setFolders(Set<Folder> folders) {
        this.folders = folders;
    }

    public void addFolder(Folder folder) {
        this.folders.add(folder);
    }

    public void setFavorites(Set<Favorite> favorites) {
        this.favorites = favorites;
    }

    public void addFavorite(Favorite favorite) {
        this.favorites.add(favorite);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", firstName='" + getFirstName() + "'" + ", lastName='" + getLastName()
                + "'" + ", username='" + getUsername() + "'" + ", dateOfBirth='" + getDateOfBirth() + "'" + ", email='"
                + getEmail() + "'" + ", bio='" + getBio() + "'" + ", country='" + getCountry() + "'" + ", password='"
                + getPassword() + "'" + ", discussions='" + getDiscussions() + "'" + ", folders='" + getFolders() + "'"
                + ", favorites='" + getFavorites() + "'" + "}";
    }
}
