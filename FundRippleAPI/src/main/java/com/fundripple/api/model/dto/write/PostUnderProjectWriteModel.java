package com.fundripple.api.model.dto.write;

import com.fundripple.api.model.enums.PostStatus;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUnderProjectWriteModel {
    private String post;
    private String url;
}
