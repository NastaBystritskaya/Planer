package com.zinoveva.planer.repositories;

import com.zinoveva.planer.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
