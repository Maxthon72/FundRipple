package com.fundripple.api.model.dto.write;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUnderUserWriteModel {
    private String post;
    private String url;
}
