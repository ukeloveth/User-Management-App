package com.assessment.user.management.app.repository;

import com.assessment.user.management.app.model.Task;
import com.assessment.user.management.app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Boolean existsByTitle(String title);
    Page<Task> findAllTasksByUser(User user, Pageable pageable);
    List<Task> findByUserId(Long userId);

}
