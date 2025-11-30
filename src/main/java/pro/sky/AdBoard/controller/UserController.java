package pro.sky.AdBoard.controller;

import pro.sky.AdBoard.dto.NewPasswordDto;
import pro.sky.AdBoard.dto.UpdateUserDto;
import pro.sky.AdBoard.dto.UserDto;
import pro.sky.AdBoard.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users", description = "User management endpoints")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Update user password",
            description = "Change password for authenticated user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password successfully updated"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PostMapping("/set_password")
    public void setPassword(@Valid @RequestBody NewPasswordDto newPasswordDto) {
        userService.updatePassword(newPasswordDto);
    }

    @Operation(
            summary = "Get current user",
            description = "Retrieve information about authenticated user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User information retrieved"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @GetMapping("/me")
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @Operation(
            summary = "Update user information",
            description = "Update profile information for authenticated user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User information updated"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PatchMapping("/me")
    public UpdateUserDto updateUser(@Valid @RequestBody UpdateUserDto updateUserDto) {
        return userService.updateUser(updateUserDto);
    }

    @Operation(
            summary = "Update user avatar",
            description = "Upload new avatar image for authenticated user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Avatar successfully updated"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateUserImage(@RequestParam("image") MultipartFile image) throws IOException {
        userService.updateUserImage(image.getBytes(), image.getContentType());
    }
}