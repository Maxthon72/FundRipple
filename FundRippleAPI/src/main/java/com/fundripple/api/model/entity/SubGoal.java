package com.fundripple.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne()
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
