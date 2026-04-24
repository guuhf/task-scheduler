package com.guuh.taskscheduler.business;


import com.guuh.taskscheduler.business.mapper.TaskMapper;
import com.guuh.taskscheduler.business.dtos.TaskDTO;
import com.guuh.taskscheduler.infraestructure.entity.Task;
import com.guuh.taskscheduler.infraestructure.enums.NotificationStatusEnum;
import com.guuh.taskscheduler.infraestructure.exceptions.IllegalDateException;
import com.guuh.taskscheduler.infraestructure.exceptions.TaskNotFoundException;
import com.guuh.taskscheduler.infraestructure.repository.TaskRepository;
import com.guuh.taskscheduler.infraestructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskMapper mapper;
    private final TaskRepository taskRepository;
    private final JwtUtil jwtUtil;

    public TaskDTO createTask(TaskDTO taskDto) {
        taskDto.setCreationDate(LocalDateTime.now());
        taskDto.setNotificationStatusEnum(NotificationStatusEnum.PENDING);
        taskDto.setEmail(getEmail());
        validateEventDate(taskDto);
        Task task = mapper.toTask(taskDto);
        return mapper.toTaskDto(taskRepository.save(task));
    }

    public void validateEventDate(TaskDTO taskDto) {
        if (taskDto.getEventDate().isBefore(taskDto.getCreationDate())) {
            throw new IllegalDateException("Event date cannot be before creation date");

        }
    }

    public String getEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<TaskDTO> getTaskByEventDate(LocalDateTime initialDate, LocalDateTime finalDate) {
        return mapper.toTaskDTOList(
                taskRepository.findByEventDateBetweenAndNotificationStatusEnum(initialDate,
                        finalDate,
                        NotificationStatusEnum.PENDING));
    }

    public List<TaskDTO> getMyTasks() {
        return mapper.toTaskDTOList(taskRepository.findByEmail(getEmail()));
    }

    public TaskDTO updateTask(TaskDTO taskDto, String id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task not found!"));
        if (!task.getEmail().equals(getEmail())) {
            throw new TaskNotFoundException("Task not found!");
        }
        task.setTaskName(taskDto.getTaskName());
        task.setEventDate(taskDto.getEventDate());
        task.setLastUpdate(LocalDateTime.now());
        task.setDescription(taskDto.getDescription());

        return mapper.toTaskDto(taskRepository.save(task));
    }

    public void deleteTask(String id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task not found!"));
        if (!task.getEmail().equals(getEmail())) {
            throw new TaskNotFoundException("Task not found!");
        }

        taskRepository.delete(task);
    }

    public TaskDTO updateTaskStatus(NotificationStatusEnum statusEnum, String id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task not found!"));
        if (!task.getEmail().equals(getEmail())) {
            throw new TaskNotFoundException("Task not found!");
        }

        task.setNotificationStatusEnum(statusEnum);
        return mapper.toTaskDto(taskRepository.save(task));

    }
}
