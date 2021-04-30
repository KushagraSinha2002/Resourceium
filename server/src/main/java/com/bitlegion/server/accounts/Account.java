package com.bitlegion.server.accounts;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bitlegion.server.uploads.File;
import com.bitlegion.server.uploads.Folder;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity // This tells Hibernate to make a table out of this class
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstName;

    private String lastName;

    @Column(unique = true, updatable = false)
    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;

    @Column(unique = true, updatable = false)
    private String email;

    private String bio;

    private String country;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private Collection<File> files;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private Collection<Folder> folders;

    public String getPassword() {
        return this.password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public Collection<File> getFiles() {
        return this.files;
    }

    public void setFiles(Collection<File> files) {
        this.files = files;
    }

    public Collection<Folder> getFolders() {
        return this.folders;
    }

    public void setFolders(Collection<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", firstName='" + getFirstName() + "'" + ", lastName='" + getLastName()
                + "'" + ", username='" + getUsername() + "'" + ", dateOfBirth='" + getDateOfBirth() + "'" + ", email='"
                + getEmail() + "'" + ", bio='" + getBio() + "'" + ", country='" + getCountry() + "'" + ", password='"
                + getPassword() + "'" + ", files='" + getFiles() + "'" + "}";
    }

}
