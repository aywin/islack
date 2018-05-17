package com.islack.photograph.repository.impl;

import com.islack.photograph.domain.entity.Tag;
import com.islack.photograph.repository.TagRepositoryExtension;
import com.islack.photograph.repository.TagRepositorySpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryImpl implements TagRepositoryExtension {

    @Autowired
    TagRepositorySpring tagRepositorySpring;

    @Override
    public List<Tag> findOrCreate(List<Tag> tags) {
        return tags
                .stream()
                .map(tag -> tagRepositorySpring.existsById(tag.getTag()) ? tag : tagRepositorySpring.save(tag))
                .collect(Collectors.toList());
    }
}
