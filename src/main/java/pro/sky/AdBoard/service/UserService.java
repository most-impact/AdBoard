package pro.sky.AdBoard.service;

import pro.sky.AdBoard.dto.*;

public interface UserService {
    void updatePassword(NewPasswordDto newPasswordDto);
    UserDto getCurrentUser();
    UpdateUserDto updateUser(UpdateUserDto updateUserDto);
    void updateUserImage(byte[] image, String contentType);
}