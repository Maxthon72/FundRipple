package com.fundripple.api.repository;

import com.fundripple.api.model.entity.Benefit;
import com.fundripple.api.model.entity.Payment;
import com.fundripple.api.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BenefitRepository extends JpaRepository<Benefit, Long> {
    List<Benefit> findAllByProject(Project project);

    @Query(value = "SELECT * FROM benefits where id = (SELECT benefit_id FROM benefit_user WHERE user_id=(SELECT id FROM users WHERE user_name=?1))",nativeQuery = true)
    List<Benefit> getListOfBenefitsForUser(String userName);
}
