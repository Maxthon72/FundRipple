package com.fundripple.api.model.entity;

import com.fundripple.api.model.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Projects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "responsible_user", nullable = false)
    private User responsibleUser;

    @Column(nullable = false)
    private Long goal;

    @Column(name = "money_collected",nullable = false)
    private Long moneyCollected;

    @Column(name = "number_of_supporters",nullable = false)
    private Long numberOfSupporters;

    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "date_closed", nullable = false)
    private LocalDateTime dateClosed;

    @Column(name = "planed_date_of_closing", nullable = false)
    private LocalDateTime planedDateOfClosing;


    //TO DO
    /*
    @Column(name="banner_image")
     */

    @Column(nullable = false)
    private String summery;

    @Column(name = "status",nullable = false)
    private ProjectStatus status;


}
