package com.bitlegion.server.discussions;

public class DiscussionDetails {
    private String name;

    private String description;

    private String userIDs;

    public String getUserIDs() {
        return this.userIDs;
    }

    public void setUserIDs(String userIDs) {
        this.userIDs = userIDs;
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

    @Override
    public String toString() {
        return "{" + " userIDs='" + getUserIDs() + "'" + ", name='" + getName() + "'" + ", description='"
                + getDescription() + "'" + "}";
    }

}
