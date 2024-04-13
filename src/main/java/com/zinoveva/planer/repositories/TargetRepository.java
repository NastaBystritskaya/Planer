package com.zinoveva.planer.repositories;

import com.zinoveva.planer.domain.Target;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetRepository extends JpaRepository<Target, Long> {
}
