package com.bitlegion.server.socials;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Follow {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private String followers;

    private String following;

 //   @ManyToOne(cascade = CascadeType.ALL)
 //   @JoinColumn(name = "account_id", referencedColumnName = "id")
 //   private Account account;

    public String getFollowers() {
        return followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    @Override
    public String toString() {
        return "{" + " followers='" + getFollowers() + "'" + ", following='" + getFollowing() + "'" + "}";
    }
}