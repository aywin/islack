package com.islack.oauth.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Privilege {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String description;

    public long getPrivilegeId() {
        return id;
    }

    public void setPrivilegeId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

}
