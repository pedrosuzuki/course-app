package com.epam.course.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotNull
    private String username;

    @NotNull
    private String password;

    private String fullName;
}