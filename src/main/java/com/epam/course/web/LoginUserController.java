package com.epam.course.web;

import com.epam.course.domain.User;
import com.epam.course.exception.LoginUserException;
import com.epam.course.service.LoginUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class LoginUserController {
    private final LoginUserService loginUserService;

    @Autowired
    public LoginUserController(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    @PostMapping("/signin")
    public Authentication login(@RequestBody @Valid LoginDto loginDto) {
        return loginUserService.signIn(loginDto.getUsername(), loginDto.getPassword());
    }

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User signup(@RequestBody @Valid LoginDto loginDto) {
        return loginUserService.signup(loginDto.getUsername(), loginDto.getPassword(), loginDto.getFullName())
                .orElseThrow(() -> new LoginUserException("User already exists"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUsers() {
        return loginUserService.getAll();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginUserException.class)
    public String return400(LoginUserException ex) {
        log.error("Cannot complete the transaction", ex);
        return ex.getMessage();
    }
}
