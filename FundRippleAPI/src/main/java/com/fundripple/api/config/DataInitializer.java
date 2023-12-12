package com.fundripple.api.config;

import com.fundripple.api.model.entity.Tag;
import com.fundripple.api.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if the 'tags' table is empty
        if (tagRepository.count() == 0) {
            // Insert initial data
            List<String> tags = List.of("Fashion","Art","Books","Music","Food","Board Games"
                    ,"Video Games","Technology","Modern","Science-fiction","Fantasy","Health",
                    "Nature","Entertainment","Recreation","For children","Educational","Outdoor",
                    "Cinema");
            tags.forEach(value->{
                Tag tag = new Tag();
                tag.setTagName(value);
                tagRepository.save(tag);
            });

            System.out.println("Initial data inserted into the 'tags' table.");
        }
    }
}