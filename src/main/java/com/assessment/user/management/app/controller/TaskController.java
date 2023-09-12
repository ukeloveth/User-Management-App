package com.assessment.user.management.app.controller;

import com.assessment.user.management.app.dto.TaskRequestDto;
import com.assessment.user.management.app.dto.TaskResponseDto;
import com.assessment.user.management.app.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PostMapping("/createTask")
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto taskRequestDto) {
        return taskService.createTask(taskRequestDto);
    }

    @GetMapping("/allTasks")
    public ResponseEntity<Map<String, Object>> getAllTasks(
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "0") Integer page) {

        return taskService.getAllTasks(size, page);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long taskId, @RequestBody TaskRequestDto taskRequestDto) {
        return taskService.updateTask(taskId, taskRequestDto);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        return taskService.deleteTask(taskId);
    }

}
