package com.fundripple.api.controller;

import com.fundripple.api.model.dto.read.ProjectReadModel;
import com.fundripple.api.model.dto.read.TagReadModel;
import com.fundripple.api.model.dto.write.ProjectDescriptionWriteModel;
import com.fundripple.api.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    @GetMapping()
    ResponseEntity<List<TagReadModel>> getAllTags(){
        return ResponseEntity.ok(tagService.getAllTags());
    }

}
