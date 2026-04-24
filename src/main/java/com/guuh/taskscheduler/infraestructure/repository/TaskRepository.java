package com.guuh.taskscheduler.infraestructure.repository;

import com.guuh.taskscheduler.business.dtos.TaskDTO;
import com.guuh.taskscheduler.infraestructure.entity.Task;
import com.guuh.taskscheduler.infraestructure.enums.NotificationStatusEnum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByEventDateBetweenAndNotificationStatusEnum (LocalDateTime initialDate,
                                                                LocalDateTime finalDate,
                                                                NotificationStatusEnum notificationStatusEnum);

    List<Task> findByEmail (String email);

}
