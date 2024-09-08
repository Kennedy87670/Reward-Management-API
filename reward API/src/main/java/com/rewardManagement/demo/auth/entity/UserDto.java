package com.rewardManagement.demo.auth.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer userId;

    @NotBlank(message = "The name field can't be blank")
    private String firstName;

    @NotBlank(message = "The name field can't be blank")
    private String lastName;

    @NotBlank(message = "The username field can't be blank")
    private String username;

    @NotBlank(message = "The email field can't be blank")
    @Email(message = "Please enter email in proper format!")
    private String email;

    @NotBlank(message = "The password field can't be blank")
    @Size(min = 5, message = "The password must have at least 5 characters")
    private String password;

    private String role;
}
