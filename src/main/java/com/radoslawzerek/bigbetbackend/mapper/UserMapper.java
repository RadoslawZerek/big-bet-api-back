package com.radoslawzerek.bigbetbackend.mapper;

import com.radoslawzerek.bigbetbackend.dto.UserDto;
import com.radoslawzerek.bigbetbackend.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User mapToUser(UserDto userDto) {
        return new User(userDto.getId(), userDto.getUsername(), userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole(), userDto.getCreated(), userDto.getBalance());
    }

    public UserDto mapToUserDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(),
                user.getRole(), user.getCreated(), user.getBalance());
    }

    public List<UserDto> mapToUserDtoList(List<User> userList) {
        return userList.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}
