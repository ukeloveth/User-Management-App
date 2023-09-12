package com.assessment.user.management.app.service.impl;

import com.assessment.user.management.app.dto.TaskRequestDto;
import com.assessment.user.management.app.dto.TaskResponseDto;
import com.assessment.user.management.app.exceptions.AuthenticatedUserNotFoundException;
import com.assessment.user.management.app.exceptions.TaskAlreadyExistException;
import com.assessment.user.management.app.exceptions.TaskNotFoundException;
import com.assessment.user.management.app.model.Task;
import com.assessment.user.management.app.model.User;
import com.assessment.user.management.app.repository.TaskRepository;
import com.assessment.user.management.app.repository.UserRepository;
import com.assessment.user.management.app.service.TaskService;
import com.assessment.user.management.app.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    @Override
    public ResponseEntity<TaskResponseDto> createTask(TaskRequestDto taskRequestDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedInUserUserName = userDetails.getUsername();

        User user = userRepository.findUserByEmail(loggedInUserUserName)
                .orElseThrow(() -> new AuthenticatedUserNotFoundException("Authenticated user not found")); // Customize the exception type

        if (taskRepository.existsByTitle(taskRequestDto.getTitle())) {
            throw new TaskAlreadyExistException("Task with the title already exists");
        }

        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setUser(user);

        Task createdTask = taskRepository.save(task);

        TaskResponseDto taskResponse = new TaskResponseDto();
        taskResponse.setId(createdTask.getId());
        taskResponse.setTitle(createdTask.getTitle());
        taskResponse.setDescription(createdTask.getDescription());

        return ResponseEntity.ok(taskResponse);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAllTasks(Integer size, Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedInUserUserName = userDetails.getUsername();

        User user = userRepository.findUserByEmail(loggedInUserUserName)
                .orElseThrow(() -> new AuthenticatedUserNotFoundException("Authenticated user not found"));

        Page<Task> tasksPage = taskRepository.findAllTasksByUser(user, pageable);

        Map<String, Object> paginatedResponse = PaginationUtils.buildPaginatedResponse(
                (Page<TaskResponseDto>) tasksPage.map(this::convertToDto).getContent(), pageable, tasksPage.getTotalElements(), "tasks"
        );

        return ResponseEntity.ok(paginatedResponse);
    }




    @Override
    public ResponseEntity<TaskResponseDto> getTaskById(Long taskId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedInUserUserName = userDetails.getUsername();

        Optional<User> userOptional = userRepository.findUserByEmail(loggedInUserUserName);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            Optional<Task> taskOptional = taskRepository.findById(taskId);

            if (taskOptional.isPresent()) {
                Task task = taskOptional.get();

                if (task.getUser().equals(user)) {
                    TaskResponseDto taskResponse = convertToDto(task);

                    return ResponseEntity.ok(taskResponse);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            } else {
                throw new TaskNotFoundException("Task not found with ID: " + taskId);
            }
        } else {
            throw new AuthenticatedUserNotFoundException("Authenticated user not found");
        }
    }




    @Override
    public ResponseEntity<TaskResponseDto> updateTask(Long taskId, TaskRequestDto taskRequestDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedInUserUserName = userDetails.getUsername();

        Optional<User> userOptional = userRepository.findUserByEmail(loggedInUserUserName);

        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (userOptional.isPresent() && taskOptional.isPresent()) {
            User user = userOptional.get();
            Task task = taskOptional.get();

            if (task.getUser().equals(user)) {
                task.setTitle(taskRequestDto.getTitle());
                task.setDescription(taskRequestDto.getDescription());

                Task updatedTask = taskRepository.save(task);

                TaskResponseDto taskResponse = new TaskResponseDto();
                taskResponse.setId(updatedTask.getId());
                taskResponse.setTitle(updatedTask.getTitle());
                taskResponse.setDescription(updatedTask.getDescription());

                return ResponseEntity.ok(taskResponse);
            } else {
                return new ResponseEntity("You do not have permission to update this task", HttpStatus.FORBIDDEN);
            }
        } else {
            throw new TaskNotFoundException("Task not found with ID: " + taskId);
        }
    }

    @Override
    public ResponseEntity<?> deleteTask(Long taskId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedInUserUserName = userDetails.getUsername();

        Optional<User> userOptional = userRepository.findUserByEmail(loggedInUserUserName);

        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if (userOptional.isPresent() && taskOptional.isPresent()) {
            User user = userOptional.get();
            Task task = taskOptional.get();

            if (task.getUser().equals(user)) {
                taskRepository.delete(task);

                return new ResponseEntity<>("Task deleted successfully", HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            throw new TaskNotFoundException("Task not found with ID: " + taskId);
        }
    }

    private TaskResponseDto convertToDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        return dto;
    }

}
