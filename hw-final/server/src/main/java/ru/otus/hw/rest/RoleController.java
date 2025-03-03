package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.RoleDto;
import ru.otus.hw.services.RoleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/api/v1/role")
    public List<RoleDto> getRoles(){
        return roleService.findAll();
    }

    @GetMapping("/api/v1/role/{id}")
    public RoleDto getRole(@PathVariable String id) {
        return roleService.findById(id);
    }

}
