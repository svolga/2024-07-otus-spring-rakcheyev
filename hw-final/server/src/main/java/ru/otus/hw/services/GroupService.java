package ru.otus.hw.services;

import ru.otus.hw.dto.GroupDto;
import ru.otus.hw.dto.GroupInfoDto;

import java.util.List;
import java.util.Set;

public interface GroupService {
    List<GroupInfoDto> findAll();

    GroupDto findById(long id);

    List<GroupDto> findAllByIds(Set<Long> ids);

    GroupDto create(GroupDto groupDto);

    GroupDto update(GroupDto groupDto);

    void delete(long id);
}
