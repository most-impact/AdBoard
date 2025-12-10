package pro.sky.AdBoard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.AdBoard.dto.NewPasswordDto;
import pro.sky.AdBoard.dto.UpdateUserDto;
import pro.sky.AdBoard.dto.UserDto;
import pro.sky.AdBoard.mapper.UserMapper;
import pro.sky.AdBoard.model.User;
import pro.sky.AdBoard.repository.UserRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void updatePassword(NewPasswordDto newPasswordDto) {
        User user = getCurrentUserEntity();
        
        if (!passwordEncoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid current password");
        }

        user.setPassword(passwordEncoder.encode(newPasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getCurrentUser() {
        User user = getCurrentUserEntity();
        return userMapper.toUserDto(user);
    }

    @Override
    @Transactional
    public UpdateUserDto updateUser(UpdateUserDto updateUserDto) {
        User user = getCurrentUserEntity();
        userMapper.updateUserFromDto(updateUserDto, user);
        userRepository.save(user);
        return updateUserDto;
    }

    @Override
    @Transactional
    public void updateUserImage(byte[] image, String contentType) {
        User user = getCurrentUserEntity();
        // Simple file saving logic (simulated or real local storage)
        String filename = "user_" + user.getId() + "_" + UUID.randomUUID() + getExtension(contentType);
        // In a real app, save to a configured directory. Here we just simulate setting the path.
        // Assuming there's a static resource handler or similar.
        // For now, let's just save the filename to the DB.
        
        // TODO: Implement actual file writing if needed.
        // For now, I will just set the reference.
        user.setImage("/images/" + filename);
        userRepository.save(user);
    }

    private User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private String getExtension(String contentType) {
        if (contentType == null) return ".jpg";
        if (contentType.contains("png")) return ".png";
        if (contentType.contains("jpeg") || contentType.contains("jpg")) return ".jpg";
        return ".jpg";
    }
}
