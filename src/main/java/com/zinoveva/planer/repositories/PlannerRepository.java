package com.zinoveva.planer.repositories;

import com.zinoveva.planer.domain.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
}
