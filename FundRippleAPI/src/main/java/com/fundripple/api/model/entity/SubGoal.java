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
public class SubGoals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "money_goal")
    private Long moneyGoal;

    @Column()
    private String description;

    @ManyToOne()
    @JoinColumn(name = "project_id", nullable = false)
    private Projects project;
}
