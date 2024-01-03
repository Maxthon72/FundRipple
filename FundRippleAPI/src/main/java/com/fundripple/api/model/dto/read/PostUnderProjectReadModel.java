package com.fundripple.api.model.dto.read;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUnderProjectReadModel {
    private Long id;
    private String post;
    private String url;
    private Long likes;
    private LocalDate dateCreated;
}
