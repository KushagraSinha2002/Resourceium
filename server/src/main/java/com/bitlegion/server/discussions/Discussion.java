package com.bitlegion.server.discussions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

    private String inviteString;

    @ManyToOne
    private Account createdBy;

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public Integer getNumPosts() {
        return this.posts.size();
    }

    public String getImageURL() {
        return "https://picsum.photos/seed/" + this.getId() + "/500";
    }

    public void setInviteString() {
        String str = UUID.randomUUID().toString();
        str = str.substring(0, Math.min(str.length(), 7));
        this.inviteString = str;
    }

}
