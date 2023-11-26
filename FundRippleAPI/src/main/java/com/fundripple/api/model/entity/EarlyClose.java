package com.fundripple.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "early_close")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EarlyClose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "project_id", nullable = false) // Use @JoinColumn to specify the column mapping
    private Project project;

    @Column(nullable = false)
    private String reason;
}
