package com.fundripple.api.service;

import com.fundripple.api.mapper.ProjectMapper;
import com.fundripple.api.model.dto.read.ProjectReadModel;
import com.fundripple.api.model.dto.write.PaymentWriteModel;
import com.fundripple.api.model.entity.Payment;
import com.fundripple.api.model.entity.Project;
import com.fundripple.api.model.entity.User;
import com.fundripple.api.repository.PaymentRepository;
import com.fundripple.api.repository.ProjectRepository;
import com.fundripple.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final ProjectMapper projectMapper;

    public PaymentService(ProjectRepository projectRepository,
                          UserRepository userRepository,
                          PaymentRepository paymentRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.projectMapper = projectMapper;
    }

    public ProjectReadModel addPayment(PaymentWriteModel paymentWriteModel){
        Project project = projectRepository.findProjectByProjectName(paymentWriteModel.getProjectName());
        project.setMoneyCollected(project.getMoneyCollected()+ paymentWriteModel.getMoney());
        Payment payment = new Payment();
        payment.setMoney(paymentWriteModel.getMoney());
        payment.setProject(project);
        payment.setUser(userRepository.findByUserName(paymentWriteModel.getUserName()).get());
        List<Payment> payments= paymentRepository.getListOfUsersSupportingProject(paymentWriteModel.getProjectName());
        if(payments.isEmpty()) {
            project.setNumberOfSupporters(project.getNumberOfSupporters()+1);
        }
        paymentRepository.save(payment);
        projectRepository.save(project);
        return projectMapper.toReadModel(project);
    }
}
