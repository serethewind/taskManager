package com.serethewind.taskmanager.service;

import com.serethewind.taskmanager.dto.AuthLoginDto;
import com.serethewind.taskmanager.dto.AuthRegisterDto;
import com.serethewind.taskmanager.dto.AuthResponseDto;

public interface AuthServiceInterface {

    String registerUser(AuthRegisterDto authRegisterDto);

    AuthResponseDto loginUser(AuthLoginDto authLoginDto);
}
