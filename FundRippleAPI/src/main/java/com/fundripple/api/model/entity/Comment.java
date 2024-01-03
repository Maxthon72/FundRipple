package com.fundripple.api.model.entity;

import com.fundripple.api.model.enums.CommentStatus;
import com.fundripple.api.model.enums.PostStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_user")
    private User user;

    @Column(nullable = false,name = "date_created")
    @GeneratedValue()
    private LocalDateTime dateCreated;

    @Column(nullable = false)
    @GeneratedValue()
    @Enumerated(EnumType.STRING)
    private CommentStatus status;
    @Column(nullable = false)
    private String comment;

    @Column(nullable = false,name = "comment_status")
    private CommentStatus commentStatus;

    @PrePersist
    public void prePersist() {
        dateCreated = LocalDateTime.now();
        status = CommentStatus.OK;
    }
}
