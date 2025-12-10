package pro.sky.AdBoard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.AdBoard.dto.LoginDto;
import pro.sky.AdBoard.dto.RegisterDto;
import pro.sky.AdBoard.mapper.UserMapper;
import pro.sky.AdBoard.model.User;
import pro.sky.AdBoard.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void register(RegisterDto registerDto) {
        log.info("Attempting to register user: {}", registerDto.getUsername());
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            log.error("User with username {} already exists", registerDto.getUsername());
            throw new IllegalArgumentException("User with this username already exists");
        }

        User user = userMapper.fromRegisterDto(registerDto, passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);
        log.info("User {} successfully saved to database", user.getUsername());
    }

    @Override
    public void login(LoginDto loginDto) {
        log.info("Attempting to login user: {}", loginDto.getUsername());
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> {
                    log.error("User not found: {}", loginDto.getUsername());
                    return new IllegalArgumentException("User not found");
                });

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            log.error("Invalid password for user: {}", loginDto.getUsername());
            throw new IllegalArgumentException("Invalid password");
        }
        log.info("User {} logged in successfully", loginDto.getUsername());
    }
}
