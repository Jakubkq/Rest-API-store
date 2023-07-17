package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public User mapToUser(final UserDto userDto) {
        return new User(userDto.getName());
    }

    public UserDto mapToUserDto(final User user) {
        return new UserDto( user.getName()  );
    }
}
