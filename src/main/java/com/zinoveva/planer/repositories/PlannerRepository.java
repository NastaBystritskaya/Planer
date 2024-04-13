package com.zinoveva.planer.repositories;

import com.zinoveva.planer.domain.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с планером в базе данных
 */
public interface PlannerRepository extends JpaRepository<Planner, Long> {



}
