package pro.sky.AdBoard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pro.sky.AdBoard.dto.LoginDto;
import pro.sky.AdBoard.dto.RegisterDto;
import pro.sky.AdBoard.mapper.UserMapper;
import pro.sky.AdBoard.model.User;
import pro.sky.AdBoard.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterDto registerDto) {
        log.info("Registering new user: {}", registerDto.getUsername());

        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        User user = userMapper.fromRegisterDto(registerDto, encodedPassword);
        userRepository.save(user);
    }

    @Override
    public void login(LoginDto loginDto) {
        log.info("Login attempt for user: {}", loginDto.getUsername());
        // Здесь можно будет реализовать реальную аутентификацию и выдачу JWT.
        // На этом этапе достаточно того, что есть register + сохранение в БД.
    }
}
