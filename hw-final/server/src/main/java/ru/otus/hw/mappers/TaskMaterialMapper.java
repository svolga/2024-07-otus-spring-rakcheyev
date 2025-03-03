package ru.otus.hw.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.otus.hw.dto.TaskMaterialDto;
import ru.otus.hw.models.Task;
import ru.otus.hw.models.TaskMaterial;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMaterialMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "url", target = "url")
    @Mapping(source = "task", target = "taskId", qualifiedByName = "getTaskId")
    TaskMaterialDto toDto(TaskMaterial taskMaterial);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "url", target = "url")
    @Mapping(source = "name", target = "name")
    @Mapping(target = "task", ignore = true)
    default TaskMaterial toEntity(TaskMaterialDto taskMaterialDto, Task task) {
        TaskMaterial taskMaterial = new TaskMaterial();
        taskMaterial.setId(taskMaterialDto.getId());
        taskMaterial.setName(taskMaterialDto.getName());
        taskMaterial.setUrl(taskMaterialDto.getUrl());
        taskMaterial.setTask(task);
        return taskMaterial;
    }

    List<TaskMaterialDto> toDtos(List<TaskMaterial> taskMaterials);
    List<TaskMaterial> toEntities(List<TaskMaterialDto> taskMaterialDtos);

    @Named("getTaskId")
    default long getTaskId(Task task) {
        return task.getId();
    }

}