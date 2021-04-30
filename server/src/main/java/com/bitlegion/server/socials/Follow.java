package com.bitlegion.server.socials;

public class Follow {
    
    private String followers;

    private String following;

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