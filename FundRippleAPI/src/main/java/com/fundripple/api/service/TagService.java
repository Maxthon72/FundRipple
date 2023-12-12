package com.fundripple.api.service;

import com.fundripple.api.mapper.TagMapper;
import com.fundripple.api.model.dto.read.TagReadModel;
import com.fundripple.api.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public List<TagReadModel> getAllTags(){
        return tagMapper.map(tagRepository.findAll());
    }
}
