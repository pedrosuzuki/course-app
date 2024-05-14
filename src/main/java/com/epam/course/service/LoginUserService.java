package com.epam.course.service;

import com.epam.course.domain.Role;
import com.epam.course.domain.User;
import com.epam.course.repository.LoginUserRepository;
import com.epam.course.repository.UserRoleRepository;
import com.epam.course.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LoginUserService {
    private static final String ROLE_NAME_REP = "ROLE_REP";
    private final AuthenticationManager authenticationManager;
    private final LoginUserRepository loginUserRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Autowired
    public LoginUserService(AuthenticationManager authenticationManager,
                            LoginUserRepository loginUserRepository,
                            UserRoleRepository userRoleRepository,
                            PasswordEncoder passwordEncoder,
                            JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.loginUserRepository = loginUserRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public Optional<String> signIn(String username, String password) {
        log.info("New user attempting to sign in");
        Optional<String> token = Optional.empty();
        Optional<User> user = loginUserRepository.findByUsername(username);
        if (user.isPresent()) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
            } catch (AuthenticationException e){
                log.info("Log in failed for user {}", username);
            }
        }
        return token;
    }

    public Optional<User> signup(String username, String password, String fullName) {
        log.info("New user attempting to sign in");
        Optional<User> user = Optional.empty();
        if (loginUserRepository.findByUsername(username).isEmpty()) {
            Optional<Role> role = userRoleRepository.findByName(ROLE_NAME_REP);
            user = Optional.of(loginUserRepository.save(new User(username,
                    passwordEncoder.encode(password),
                    role.get(),
                    fullName)));
        }
        return user;
    }

    public List<User> getAll() {
        return loginUserRepository.findAll();
    }
}
