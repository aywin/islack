package com.islack.photograph.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tag {
    @Id
    private String tag;

    public Tag() {}

    public Tag(String tag) {
        this.tag = tag.toLowerCase();
    }

    public String getTag() {
        return tag.toLowerCase();
    }

    public void setTag(String tag) {
        this.tag = tag.toLowerCase();
    }

}
