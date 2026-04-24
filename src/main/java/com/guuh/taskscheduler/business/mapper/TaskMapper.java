package com.guuh.taskscheduler.business.mapper;

import com.guuh.taskscheduler.business.dtos.TaskDTO;
import com.guuh.taskscheduler.infraestructure.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "id" , target = "id")
    Task toTask (TaskDTO taskDTO);
    TaskDTO toTaskDto (Task task);

    List<Task> toTaskList(List<TaskDTO> taskDTOList);
    List<TaskDTO> toTaskDTOList(List<Task> taskList);

}
