package com.fundripple.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "benefits")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Benefit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column()
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "benefit_user",
            joinColumns = @JoinColumn(name = "benefit_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @ManyToOne()
    @JoinColumn()
    private Project project;

    @Column(name = "money_goal")
    private Long moneyGoal;

    @Column()
    private String description;

    @Column()
    private String name;
}
