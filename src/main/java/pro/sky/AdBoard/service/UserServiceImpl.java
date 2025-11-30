package pro.sky.AdBoard.service;

import org.springframework.stereotype.Service;
import pro.sky.AdBoard.dto.*;

@Service
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND = "User not found";

    @Override
    public void updatePassword(NewPasswordDto newPasswordDto) {
    }

    @Override
    public UserDto getCurrentUser() {
        UserDto userDto = new UserDto();
        return userDto;
    }

    @Override
    public UpdateUserDto updateUser(UpdateUserDto updateUserDto) {
        return updateUserDto;
    }

    @Override
    public void updateUserImage(byte[] image, String contentType) {
    }
}
