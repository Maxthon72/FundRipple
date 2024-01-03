package com.fundripple.api.repository;

import com.fundripple.api.model.entity.EarlyClose;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EarlyCloseRepository extends JpaRepository<EarlyClose, Long> {
}
