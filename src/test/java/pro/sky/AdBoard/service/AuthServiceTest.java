package pro.sky.AdBoard.service;

import pro.sky.AdBoard.dto.LoginDto;
import pro.sky.AdBoard.dto.RegisterDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void register_WithValidData_ShouldNotThrowException() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("testuser");
        registerDto.setPassword("password123");
        registerDto.setFirstName("John");
        registerDto.setLastName("Doe");
        registerDto.setPhone("+79991234567");
        registerDto.setRole("USER");

        assertDoesNotThrow(() -> authService.register(registerDto));
    }

    @Test
    void login_WithValidData_ShouldNotThrowException() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("testuser");
        loginDto.setPassword("password123");

        assertDoesNotThrow(() -> authService.login(loginDto));
    }
}