package com.fundripple.api.service;

import com.fundripple.api.error.PostException;
import com.fundripple.api.error.ProjectException;
import com.fundripple.api.mapper.PostMapper;
import com.fundripple.api.model.dto.read.PostUnderProjectReadModel;
import com.fundripple.api.model.dto.read.PostUnderUserReadModel;
import com.fundripple.api.model.dto.read.UserReadModel;
import com.fundripple.api.model.dto.write.PostUnderProjectWriteModel;
import com.fundripple.api.model.dto.write.PostUnderUserWriteModel;
import com.fundripple.api.model.entity.PostUnderProject;
import com.fundripple.api.model.entity.PostUnderUser;
import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.entity.User;
import com.fundripple.api.repository.PostUnderProjectRepository;
import com.fundripple.api.repository.PostUnderUserRepository;
import com.fundripple.api.repository.ProjectRepository;
import com.fundripple.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PostService {
    private final PostUnderProjectRepository postUnderProjectRepository;
    private final PostUnderUserRepository postUnderUserRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PostMapper postMapper;

    public PostService(PostUnderProjectRepository postUnderProjectRepository, PostUnderUserRepository postUnderUserRepository,
                       ProjectRepository projectRepository, UserRepository userRepository, UserService userService, PostMapper postMapper) {
        this.postUnderProjectRepository = postUnderProjectRepository;
        this.postUnderUserRepository = postUnderUserRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.postMapper = postMapper;
    }

    public PostUnderProjectReadModel addPostUnderProject(String projectName, PostUnderProjectWriteModel postUnderProjectWriteModel,String header){
        try {
            Project project = projectRepository.findProjectByProjectName(projectName);
            if (Objects.equals(project.getResponsibleUser().getUsername(), userService.getUserDtoByToken(header).getUserName())) {
                PostUnderProject postUnderProject = postMapper.toEntity(postUnderProjectWriteModel);
                postUnderProject.setProject(project);
                postUnderProjectRepository.save(postUnderProject);
                return postMapper.toDto(postUnderProject);
            }
            throw new PostException("Failed to add post project does not belong to user");
        } catch (Exception e) {
            throw new PostException("Failed to add post due to: " + e.getMessage());
        }
    }

    public PostUnderUserReadModel addPostUnderUser(PostUnderUserWriteModel postUnderUserWriteModel, String header){
        try {
            User user = userService.getUserEntityByToken(header);
                PostUnderUser postUnderUser = postMapper.toEntity(postUnderUserWriteModel);
            postUnderUser.setUser(user);
                postUnderUserRepository.save(postUnderUser);
                return postMapper.toDto(postUnderUser);
        } catch (Exception e) {
            throw new PostException("Failed to add post due to: " + e.getMessage());
        }
    }

    public List<PostUnderProjectReadModel> getPostsForProject(String projectName){
        Project project = projectRepository.findProjectByProjectName(projectName);
        List<PostUnderProject> postUnderProjectList = postUnderProjectRepository.getAllByProject(project);
        return postMapper.mapProjects(postUnderProjectList);
    }

    public List<PostUnderUserReadModel> getPostsForUser(String userName){
        User user = userRepository.findByUserName(userName).get();
        List<PostUnderUser> postUnderUsers = postUnderUserRepository.getAllByUser(user);
        return postMapper.mapUsers(postUnderUsers);
    }

    public String likePostUnderProject(Long postId){
        PostUnderProject postUnderProject = postUnderProjectRepository.getReferenceById(postId);
        postUnderProject.setLikes(postUnderProject.getLikes()+1);
        postUnderProjectRepository.save(postUnderProject);
        return "OK";
    }

    public String likePostUnderUser(Long postId){
        PostUnderUser postUnderUser = postUnderUserRepository.getReferenceById(postId);
        postUnderUser.setLikes(postUnderUser.getLikes()+1);
        postUnderUserRepository.save(postUnderUser);
        return "OK";
    }

    public String removeLikePostUnderProject(Long postId){
        PostUnderProject postUnderProject = postUnderProjectRepository.getReferenceById(postId);
        postUnderProject.setLikes(postUnderProject.getLikes()-1);
        postUnderProjectRepository.save(postUnderProject);
        return "OK";
    }

    public String removeLikePostUnderUser(Long postId){
        PostUnderUser postUnderUser = postUnderUserRepository.getReferenceById(postId);
        postUnderUser.setLikes(postUnderUser.getLikes()-1);
        postUnderUserRepository.save(postUnderUser);
        return "OK";
    }
}
