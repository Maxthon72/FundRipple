package com.fundripple.api.model.entity;

import com.fundripple.api.model.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "sub_goals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "money_goal",nullable = false)
    private Long moneyGoal;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String name;

    @Column()
    @GeneratedValue()
    private Boolean met;

    @ManyToOne()
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @PrePersist
    public void prePersist() {
        met = false;
    }
}
