package com.bitlegion.server.discussions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.bitlegion.server.accounts.Account;
import com.bitlegion.server.socials.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Discussion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String name;

    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "discussions")
    private Set<Account> accounts = new HashSet<Account>();

    @JsonIgnore
    @OneToMany(mappedBy = "discussion")
    private Collection<Post> posts = new ArrayList<Post>();

    @ManyToOne
    private Account createdBy;

    public Integer getId() {
        return this.id;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Account> getAccounts() {
        return this.accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public Collection<Post> getPosts() {
        return this.posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

    public Account getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Account createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", description='" + getDescription() + "'"
                + ", accounts='" + getAccounts() + "'" + ", posts='" + getPosts() + "'" + ", createdBy='"
                + getCreatedBy() + "'" + "}";
    }

}
