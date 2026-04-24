package com.guuh.taskscheduler.controller;

import com.guuh.taskscheduler.business.TaskService;
import com.guuh.taskscheduler.business.dtos.TaskDTO;
import com.guuh.taskscheduler.infraestructure.entity.Task;
import com.guuh.taskscheduler.infraestructure.enums.NotificationStatusEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid TaskDTO taskDTO){
        return ResponseEntity.status(201).body(taskService.createTask(taskDTO));
    }

    @GetMapping("/events")
    public ResponseEntity<List<TaskDTO>> getTaskByEventDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime initialDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime finalDate){
        return ResponseEntity.status(200).body(taskService.getTaskByEventDate(initialDate, finalDate));
    }

    @GetMapping("/my-tasks")
    public ResponseEntity<List<TaskDTO>>getMyTasks(){
        return ResponseEntity.status(200).body(taskService.getMyTasks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO>updateTasks(@RequestBody TaskDTO taskDto,
                                              @PathVariable String id){
        return ResponseEntity.status(200).body(taskService.updateTask(taskDto, id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskDTO>updateTaskStatus(@RequestParam("status")NotificationStatusEnum statusEnum,
                                                   @PathVariable String id){
        return ResponseEntity.status(200).body(taskService.updateTaskStatus(statusEnum, id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteTasks(@PathVariable String id){
        taskService.deleteTask(id);
        return ResponseEntity.status(204).build();
    }
}
