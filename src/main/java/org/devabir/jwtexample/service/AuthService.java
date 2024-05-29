package org.devabir.jwtexample.service;

import org.devabir.jwtexample.model.UserDao;
import org.devabir.jwtexample.model.UserRequestDto;
import org.devabir.jwtexample.model.UserResponseDto;
import org.devabir.jwtexample.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public UserResponseDto register(UserRequestDto userRequestDto) {
        final String email = userRequestDto.getEmail();
        if (this.userRepository.findById(email).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email " + email + " is already taken.");

        UserDao userDao = new UserDao();
        BeanUtils.copyProperties(userRequestDto, userDao, "password");
        userDao.setHashedPassword(this.passwordEncoder.encode(userRequestDto.getPassword()));
        userDao = this.userRepository.save(userDao);
        return this.userService.toDto(userDao);
    }

    public UserResponseDto login(UserRequestDto userRequestDto) {
        final String email = userRequestDto.getEmail();
        UserDao userDao = userRepository.
                findById(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User " + email + " not found.")
                );

        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequestDto.getEmail(),
                        userRequestDto.getPassword()
                )
        );

        return userService.toDto(userDao);
    }

    public UserResponseDto profile(String email) {
        UserDao userDao = userRepository
                .findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found."));
        return userService.toDto(userDao);
    }

}
