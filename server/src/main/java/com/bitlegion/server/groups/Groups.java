package com.bitlegion.server.groups;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String groupname;

    private String description;

    public Integer getId() {
        return id;
    }

    public String getGroupname() {
        return groupname;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
