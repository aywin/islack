package com.islack.photograph.repository;


import com.islack.photograph.domain.entity.Tag;

import java.util.List;

public interface TagRepositoryExtension {
    List<Tag> findOrCreate(String[] tags);
}
