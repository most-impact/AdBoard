package pro.sky.AdBoard.controller;

import pro.sky.AdBoard.dto.NewPasswordDto;
import pro.sky.AdBoard.dto.UpdateUserDto;
import pro.sky.AdBoard.dto.UserDto;
import pro.sky.AdBoard.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void setPassword_WithValidData_ShouldReturnOk() throws Exception {
        NewPasswordDto passwordDto = new NewPasswordDto();
        passwordDto.setCurrentPassword("oldPass123");
        passwordDto.setNewPassword("newPass456");

        doNothing().when(userService).updatePassword(any(NewPasswordDto.class));

        mockMvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDto)))
                .andExpect(status().isOk());
    }

    @Test
    void getCurrentUser_ShouldReturnUser() throws Exception {
        UserDto userDto = new UserDto();
        when(userService.getCurrentUser()).thenReturn(userDto);

        mockMvc.perform(get("/users/me"))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser_WithValidData_ShouldReturnOk() throws Exception {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setFirstName("John");
        updateUserDto.setLastName("Smith");
        updateUserDto.setPhone("+79991234567");

        when(userService.updateUser(any(UpdateUserDto.class))).thenReturn(updateUserDto);

        mockMvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isOk());
    }

    @Test
    void updateUserImage_WithValidImage_ShouldReturnOk() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        doNothing().when(userService).updateUserImage(any(byte[].class), anyString());

        mockMvc.perform(multipart("/users/me/image")
                        .file(imageFile))
                .andExpect(status().isOk());
    }
}