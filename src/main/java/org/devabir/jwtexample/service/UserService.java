package org.devabir.jwtexample.service;

import org.devabir.jwtexample.model.UserDao;
import org.devabir.jwtexample.model.UserResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserResponseDto toDto(UserDao userDao) {
        UserResponseDto result = new UserResponseDto();
        BeanUtils.copyProperties(userDao, result, "hashedPassword");
        return result;
    }

}
