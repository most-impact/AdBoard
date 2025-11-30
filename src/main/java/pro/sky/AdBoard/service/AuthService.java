package pro.sky.AdBoard.service;

import pro.sky.AdBoard.dto.LoginDto;
import pro.sky.AdBoard.dto.RegisterDto;

public interface AuthService {
    void register(RegisterDto registerDto);
    void login(LoginDto loginDto);
}