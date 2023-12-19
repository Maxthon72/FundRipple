package com.fundripple.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "suspicions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Suspicion {
    @Id
    private Long id;

    @Column()
    private String projectName;

    @Column()
    private String userName;
}
