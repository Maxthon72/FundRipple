package com.fundripple.api.model.entity;

import com.fundripple.api.model.enums.PostStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String post;

    @Column(nullable = false,name = "date_created")
    @GeneratedValue()
    private LocalDate dateCreated;

    @Column(nullable = false)
    @GeneratedValue()
    private PostStatus status;

    @Column(name = "image")
    private String url;

    @Column(nullable = false)
    @GeneratedValue()
    private Long likes;

    @PrePersist
    public void prePersist() {

        dateCreated = LocalDate.now();
        likes=0L;
        status = PostStatus.OK;
    }
}
