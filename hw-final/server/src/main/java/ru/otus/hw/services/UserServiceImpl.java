package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserInfoDto;
import ru.otus.hw.dto.UserTeacherProfileDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.UserMapper;
import ru.otus.hw.models.Role;
import ru.otus.hw.models.User;
import ru.otus.hw.models.UserTeacherProfile;
import ru.otus.hw.repositories.RoleRepository;
import ru.otus.hw.repositories.TeacherProfileRepository;
import ru.otus.hw.repositories.UserRepository;
import ru.otus.hw.security.RoleKey;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;
    private final TeacherProfileRepository teacherProfileRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDto findById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id %d not found".formatted(id)));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserTeacherProfileDto findTeacherById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id %d not found".formatted(id)));
        return userMapper.toUserTeacherProfileDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserInfoDto> findAll() {
        var users = userRepository.findAll();

/*        for (User user : users) {
            user.getRoles().size();
        }*/

        return userMapper.toUserInfoDtos(users);
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        userDto.setId(0L);
        User user = userMapper.toEntity(userDto, getRoles(userDto.getRoleDtos()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDto update(UserDto userDto) {
        validate(userDto.getId());
        User user = userMapper.toEntity(userDto, getRoles(userDto.getRoleDtos()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserTeacherProfileDto updateTeacherProfile(UserTeacherProfileDto userTeacherProfileDto) {
        validate(userTeacherProfileDto.getId());
        User user = userRepository.findById(userTeacherProfileDto.getId()).get();
        UserTeacherProfile userTeacherProfile = userMapper.toUserTeacherProfileEntity(userTeacherProfileDto);
        user.setTeacherProfile(userTeacherProfile);
        userTeacherProfile.setUser(user);
        return userMapper.toUserTeacherProfileDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        validate(id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserTeacherProfileDto> findAllTeachers() {
        Role role = new Role(RoleKey.TEACHER.name());
        var users = userRepository.findAllByRoles(List.of(role));
        return userMapper.toUserTeacherProfileDtos(users);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserInfoDto> findAllStudents() {
        Role role = new Role(RoleKey.STUDENT.name());
        var users = userRepository.findAllByRoles(List.of(role));
        return userMapper.toUserInfoDtos(users);
    }

    private void validate(long id) {
        var user = findById(id);
        if (user == null) {
            throw new EntityNotFoundException("User with id %d not found".formatted(id));
        }
    }

    private Set<Role> getRoles(Set<String> rolesIds) {
        if (isEmpty(rolesIds)) {
            throw new IllegalArgumentException("Roles ids must not be null");
        }

        List<Role> roles = roleRepository.findAllByRoleIn(rolesIds);

        if (isEmpty(roles) || rolesIds.size() != roles.size()) {
            throw new EntityNotFoundException("Roles with ids %s not found".formatted(rolesIds));
        }

        Set<Role> targetSet = new HashSet<>();
        targetSet.addAll(roles);
        return targetSet;
    }

}
