package com.fundripple.api.model.dto.write;

import com.fundripple.api.model.enums.PostStatus;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;

import java.time.LocalDateTime;

public class PostUnderProjectWriteModel {
    private String post;
    private String url;
}
