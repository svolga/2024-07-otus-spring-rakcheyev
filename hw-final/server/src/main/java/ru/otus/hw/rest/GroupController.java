package ru.otus.hw.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.GroupDto;
import ru.otus.hw.dto.GroupInfoDto;
import ru.otus.hw.services.GroupService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/api/v1/group")
    public List<GroupInfoDto> getGroups(){
        return groupService.findAll();
    }

    @GetMapping("/api/v1/group/{id}")
    public GroupDto getGroup(@PathVariable long id) {
        return groupService.findById(id);
    }

    @PostMapping("/api/v1/group")
    public GroupDto createGroup(@Valid @RequestBody GroupDto group) {
        return groupService.create(group);
    }

    @PutMapping("/api/v1/group")
    public GroupDto updateGroup(@Valid @RequestBody GroupDto group) {
        return groupService.update(group);
    }

    @DeleteMapping("/api/v1/group/{id}")
    public void deleteGroup(@PathVariable("id") long id) {
        groupService.delete(id);
    }
}
