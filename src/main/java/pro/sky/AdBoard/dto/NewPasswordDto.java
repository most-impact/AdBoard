package pro.sky.AdBoard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO for updating user password")
public class NewPasswordDto {

    @Schema(description = "Current password", minLength = 8, maxLength = 16, example = "oldPassword123")
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    private String currentPassword;

    @Schema(description = "New password", minLength = 8, maxLength = 16, example = "newPassword456")
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    private String newPassword;

    public NewPasswordDto() {}

    // Геттеры и сеттеры
    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}