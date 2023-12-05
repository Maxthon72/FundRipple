package com.fundripple.api.model.entity;

import com.fundripple.api.model.enums.ProjectDescriptionElementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "projects_descriptions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ProjectDescriptionElementType type;

    @Column(name = "index_in_description")
    private Long indexIdDescription;

    @Column()
    private String description;
}
