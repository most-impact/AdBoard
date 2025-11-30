package pro.sky.AdBoard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO for user registration")
public class RegisterDto {

    @Schema(description = "Username", minLength = 4, maxLength = 32, example = "user123")
    @Size(min = 4, max = 32, message = "Username must be between 4 and 32 characters")
    private String username;

    @Schema(description = "Password", minLength = 8, maxLength = 16, example = "password123")
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    private String password;

    @Schema(description = "First name", minLength = 2, maxLength = 16, example = "John")
    @Size(min = 2, max = 16, message = "First name must be between 2 and 16 characters")
    private String firstName;

    @Schema(description = "Last name", minLength = 2, maxLength = 16, example = "Doe")
    @Size(min = 2, max = 16, message = "Last name must be between 2 and 16 characters")
    private String lastName;

    @Schema(description = "Phone number", pattern = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}", example = "+79991234567")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}", message = "Phone number must match pattern +7 XXX XXX-XX-XX")
    private String phone;

    @Schema(description = "User role", allowableValues = {"USER", "ADMIN"}, example = "USER")
    private String role;

    public RegisterDto() {}

    // Геттеры и сеттеры
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}