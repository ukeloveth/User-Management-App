package com.assessment.user.management.app.service;

import com.assessment.user.management.app.dto.TaskRequestDto;
import com.assessment.user.management.app.dto.TaskResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TaskService {
    ResponseEntity<TaskResponseDto> createTask(TaskRequestDto taskRequestDto);
    ResponseEntity<Map<String, Object>> getAllTasks(Integer size, Integer page);
    ResponseEntity<TaskResponseDto> getTaskById(Long taskId);
    ResponseEntity<TaskResponseDto> updateTask(Long taskId, TaskRequestDto taskRequestDto);
    ResponseEntity<?> deleteTask(Long taskId);

}
