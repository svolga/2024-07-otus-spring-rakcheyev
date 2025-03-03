package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.GroupDto;
import ru.otus.hw.dto.GroupInfoDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.GroupMapper;
import ru.otus.hw.models.Course;
import ru.otus.hw.repositories.CourseRepository;
import ru.otus.hw.repositories.GroupRepository;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;

    private final GroupMapper groupMapper;

    @Override
    public List<GroupInfoDto> findAll() {
        var groups = groupRepository.findAll();
        return groupMapper.toGroupInfoDtos(groups);
    }

    @Override
    public GroupDto findById(long id) {
        var group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group with id %d not found".formatted(id)));
        return groupMapper.toDto(group);
    }

    @Override
    public List<GroupDto> findAllByIds(Set<Long> ids) {
        var groups = groupRepository.findAllByIdIn(ids);
        return groupMapper.toDtos(groups);
    }

    @Override
    @Transactional
    public GroupDto create(GroupDto groupDto) {
        groupDto.setId(0L);
        var group = groupMapper.toEntity(groupDto, getCourse(groupDto.getCourseDto().getId()));
        return groupMapper.toDto(groupRepository.save(group));
    }

    @Override
    @Transactional
    public GroupDto update(GroupDto groupDto) {
        validate(groupDto.getId());
        var group = groupMapper.toEntity(groupDto, getCourse(groupDto.getCourseDto().getId()));
        return groupMapper.toDto(groupRepository.save(group));
    }

    @Override
    @Transactional
    public void delete(long id) {
        validate(id);
        groupRepository.deleteById(id);
    }

    private void validate(long id) {
        if (!groupRepository.existsById(id)) {
            throw new EntityNotFoundException("Group with id %d not found".formatted(id));
        }
    }

    private Course getCourse(long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with id %d not found".formatted(courseId)));
    }

}