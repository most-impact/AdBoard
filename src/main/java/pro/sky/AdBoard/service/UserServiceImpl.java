package pro.sky.AdBoard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pro.sky.AdBoard.dto.NewPasswordDto;
import pro.sky.AdBoard.dto.UpdateUserDto;
import pro.sky.AdBoard.dto.UserDto;
import pro.sky.AdBoard.mapper.UserMapper;
import pro.sky.AdBoard.model.User;
import pro.sky.AdBoard.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final ImageService imageService;

    private User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }

        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public void updatePassword(NewPasswordDto newPasswordDto) {
        User user = getCurrentUserEntity();
        log.info("Updating password for user: {}", user.getUsername());

        if (!passwordEncoder.matches(newPasswordDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDto getCurrentUser() {
        User user = getCurrentUserEntity();
        log.info("Getting current user: {}", user.getUsername());
        return userMapper.toUserDto(user);
    }

    @Override
    public UpdateUserDto updateUser(UpdateUserDto updateUserDto) {
        User user = getCurrentUserEntity();
        log.info("Updating user profile for: {}", user.getUsername());

        userMapper.updateUserFromDto(updateUserDto, user);
        userRepository.save(user);

        UpdateUserDto result = new UpdateUserDto();
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setPhone(user.getPhone());
        return result;
    }

    @Override
    public void updateUserImage(byte[] image, String contentType) {
        User user = getCurrentUserEntity();
        log.info("Updating image for user: {}", user.getUsername());

        String filename = imageService.saveImage(image, contentType);
        // путь должен начинаться с корня
        user.setImage("/images/" + filename);

        userRepository.save(user);
    }
}
