package com.serethewind.taskmanager.service;

import com.serethewind.taskmanager.dto.AuthRegisterDto;

public interface AuthServiceInterface {

    String registerUser(AuthRegisterDto authRegisterDto);

    String loginUser(AuthRegisterDto authRegisterDto);
}
