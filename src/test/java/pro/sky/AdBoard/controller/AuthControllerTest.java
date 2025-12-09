package pro.sky.AdBoard.controller;

import pro.sky.AdBoard.dto.LoginDto;
import pro.sky.AdBoard.dto.RegisterDto;
import pro.sky.AdBoard.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void register_WithValidData_ShouldReturnCreated() throws Exception {
        RegisterDto registerDto = createValidRegisterDto();

        doNothing().when(authService).register(any(RegisterDto.class));

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isCreated());

        verify(authService, times(1)).register(any(RegisterDto.class));
    }

    @Test
    void login_WithValidData_ShouldReturnOk() throws Exception {
        LoginDto loginDto = createValidLoginDto();

        doNothing().when(authService).login(any(LoginDto.class));

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk());

        verify(authService, times(1)).login(any(LoginDto.class));
    }

    @Test
    void register_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        RegisterDto invalidDto = new RegisterDto(); // Empty DTO

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());

        verify(authService, never()).register(any(RegisterDto.class));
    }

    private RegisterDto createValidRegisterDto() {
        RegisterDto dto = new RegisterDto();
        dto.setUsername("testuser");
        dto.setPassword("password123");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPhone("+79991234567");
        dto.setRole("USER");
        return dto;
    }

    private LoginDto createValidLoginDto() {
        LoginDto dto = new LoginDto();
        dto.setUsername("testuser");
        dto.setPassword("password123");
        return dto;
    }
}