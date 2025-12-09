package pro.sky.AdBoard.mapper;

import org.springframework.stereotype.Component;
import pro.sky.AdBoard.dto.RegisterDto;
import pro.sky.AdBoard.dto.UpdateUserDto;
import pro.sky.AdBoard.dto.UserDto;
import pro.sky.AdBoard.model.User;
import pro.sky.AdBoard.model.UserRole;

@Component
public class UserMapper {

    public UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        // В DTO поле называется email, а в сущности username.
        // Будем считать, что username = email (логин).
        dto.setEmail(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole() != null ? user.getRole().name() : null);
        dto.setImage(user.getImage());
        return dto;
    }

    public void updateUserFromDto(UpdateUserDto dto, User user) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
    }

    public User fromRegisterDto(RegisterDto dto, String encodedPassword) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encodedPassword);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());

        if (dto.getRole() != null) {
            user.setRole(UserRole.valueOf(dto.getRole()));
        } else {
            user.setRole(UserRole.USER);
        }

        return user;
    }
}
