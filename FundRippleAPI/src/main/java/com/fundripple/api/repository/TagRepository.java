package com.fundripple.api.repository;

import com.fundripple.api.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findTagByTagName(String tagName);
}
