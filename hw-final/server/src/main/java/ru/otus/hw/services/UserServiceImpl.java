package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserInfoDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.UserMapper;
import ru.otus.hw.models.Role;
import ru.otus.hw.models.User;
import ru.otus.hw.repositories.RoleRepository;
import ru.otus.hw.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

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
    public List<UserInfoDto> findAll() {
        var users = userRepository.findAll();

        for (User user : users) {
            user.getRoles().size();
        }

        return userMapper.toUserInfoDtos(users);
    }


    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        userDto.setId(0L);
        User user = userMapper.toEntity(userDto,getRoles(userDto.getRoleDtos()));
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
    public void deleteById(long id) {
        validate(id);
        userRepository.deleteById(id);
    }

/*    private User save(long id, String title, Set<String> rolesIds) {
        if (isEmpty(rolesIds)) {
            throw new IllegalArgumentException("Role ids must not be null");
        }

        var roles = roleRepository.findAllByIdIn(rolesIds);
        if (isEmpty(roles) || rolesIds.size() != roles.size()) {
            throw new EntityNotFoundException("One or all roles with ids %s not found".formatted(rolesIds));
        }

        var user = new User(id, title, roles);
        return userRepository.save(user);
    }
*/
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
