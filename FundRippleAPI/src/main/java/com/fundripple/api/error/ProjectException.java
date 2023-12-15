package com.fundripple.api.error;

import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.entity.ProjectDescription;
import com.fundripple.api.model.entity.Tag;
import com.fundripple.api.repository.ProjectRepository;

import java.util.List;

public class ProjectException extends RuntimeException{
    public ProjectException(String message){
        super(message);
    }
}
