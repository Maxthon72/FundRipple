package com.fundripple.api.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment_under_project_post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentUnderProjectPost extends Comment{
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
