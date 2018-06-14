package com.islack.photograph.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Photograph {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uri;
    private String thumbnail;
    private Long credit;
    @ManyToMany
    private List<Tag> tags = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "photograph")
    private List<PhotographAccess> photographAccesses = new ArrayList<>();
    private String username;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdDate;
    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }

    public List<PhotographAccess> getPhotographAccesses() {
        return photographAccesses;
    }

    public void setPhotographAccesses(List<PhotographAccess> photographAccesses) {
        this.photographAccesses = photographAccesses;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
