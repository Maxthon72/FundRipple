package com.fundripple.api.config;

import com.fundripple.api.model.entity.Tag;
import com.fundripple.api.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if the 'tags' table is empty
        if (tagRepository.count() == 0) {
            // Insert initial data
            Tag tag1 = new Tag();
            tag1.setTagName("Movie");

            Tag tag2 = new Tag();
            tag2.setTagName("Video game");

            Tag tag3 = new Tag();
            tag3.setTagName("Books");

            Tag tag4 = new Tag();
            tag4.setTagName("Music");

            // Save to the database
            tagRepository.save(tag1);
            tagRepository.save(tag2);
            tagRepository.save(tag3);
            tagRepository.save(tag4);

            System.out.println("Initial data inserted into the 'tags' table.");
        }
    }
}