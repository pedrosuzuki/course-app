package com.epam.course.service;

import com.epam.course.domain.Role;
import com.epam.course.domain.User;
import com.epam.course.repository.LoginUserRepository;
import com.epam.course.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoginUserService {
    private static final String ROLE_NAME_REP = "ROLE_REP";
    private final AuthenticationManager authenticationManager;
    private final LoginUserRepository loginUserRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginUserService(AuthenticationManager authenticationManager, LoginUserRepository loginUserRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.loginUserRepository = loginUserRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Authentication signIn(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    public Optional<User> signup(String username, String password, String fullName) {
        if (loginUserRepository.findByUsername(username).isPresent()) {
            Optional<Role> role = userRoleRepository.findByName(ROLE_NAME_REP);
            return Optional.of(loginUserRepository.save
                    (new User(username,
                            passwordEncoder.encode(password),
                            role.get(),
                            fullName)));
        }
        return Optional.empty();
    }

    public List<User> getAll() {
        return loginUserRepository.findAll();
    }
}
