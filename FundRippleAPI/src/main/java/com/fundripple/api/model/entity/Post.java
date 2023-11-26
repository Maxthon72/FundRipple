package com.fundripple.api.model.entity;

import com.fundripple.api.model.enums.PostStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String post;

    @Column(nullable = false,name = "date_created")
    @GeneratedValue()
    private LocalDateTime dateCreated;

    @Column(nullable = false)
    private PostStatus status;

    @Column()
    private String url;

    @Column(nullable = false)
    private Long likes;

    @PrePersist
    public void prePersist() {
        dateCreated = LocalDateTime.now();
    }
}
