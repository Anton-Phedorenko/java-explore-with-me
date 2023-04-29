package ru.practicum.admin_access.users.service.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.admin_access.users.dto.UserDto;
import ru.practicum.admin_access.users.mapper.UserMapper;
import ru.practicum.admin_access.users.model.User;
import ru.practicum.admin_access.users.repository.UserRepository;
import ru.practicum.admin_access.users.service.dal.UserService;
import ru.practicum.exceptions.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        return UserMapper.toUserDto(repository.save(UserMapper.toUser(userDto)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.findById(id)
                .ifPresentOrElse(user -> repository.deleteById(id), () -> {
                    throw new NotFoundException(String.format("User with id=%swas not found", id));
                });
//        repository.deleteById(id);
    }

    @Override
    public List<UserDto> get(List<Long> ids, Integer from, Integer size) {
        List<User> users;
        if (ids == null) {
            users = repository.findAll(PageRequest.of(from > 0 ? from / size : 0, size)).toList();
        } else {
            users = repository.findAllById(ids);
        }

        return users.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%s was not found", id)));
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }
}
