package pro.sky.AdBoard.controller;

import pro.sky.AdBoard.dto.LoginDto;
import pro.sky.AdBoard.dto.RegisterDto;
import pro.sky.AdBoard.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication and registration endpoints")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "User registration",
            description = "Register a new user in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User successfully created"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterDto registerDto) {
        authService.register(registerDto);
    }

    @Operation(
            summary = "User login",
            description = "Authenticate user and return JWT token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginDto loginDto) {
        authService.login(loginDto);
    }
}