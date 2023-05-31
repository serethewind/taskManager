package com.serethewind.taskmanager.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    private String name;
    private String username;
    private String email;
    private String password;
    private String roles;
}
