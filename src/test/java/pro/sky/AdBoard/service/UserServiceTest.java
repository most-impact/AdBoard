package pro.sky.AdBoard.service;

import pro.sky.AdBoard.dto.NewPasswordDto;
import pro.sky.AdBoard.dto.UpdateUserDto;
import pro.sky.AdBoard.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void updatePassword_WithValidData_ShouldNotThrowException() {
        NewPasswordDto passwordDto = new NewPasswordDto();
        passwordDto.setCurrentPassword("oldPass123");
        passwordDto.setNewPassword("newPass456");

        assertDoesNotThrow(() -> userService.updatePassword(passwordDto));
    }

    @Test
    void getCurrentUser_ShouldReturnUserDto() {
        UserDto result = userService.getCurrentUser();

        assertNotNull(result);
        assertInstanceOf(UserDto.class, result);
    }

    @Test
    void updateUser_WithValidData_ShouldReturnUpdateUserDto() {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setFirstName("John");
        updateUserDto.setLastName("Smith");
        updateUserDto.setPhone("+79991234567");

        UpdateUserDto result = userService.updateUser(updateUserDto);

        assertNotNull(result);
        assertInstanceOf(UpdateUserDto.class, result);
    }
}