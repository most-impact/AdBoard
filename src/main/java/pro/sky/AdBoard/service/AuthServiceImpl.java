package pro.sky.AdBoard.service;

import org.springframework.stereotype.Service;
import pro.sky.AdBoard.dto.LoginDto;
import pro.sky.AdBoard.dto.RegisterDto;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Override
    public void register(RegisterDto registerDto) {
        log.info("Registering new user: {}", registerDto.getUsername());
    }

    @Override
    public void login(LoginDto loginDto) {
        log.info("Login attempt for user: {}", loginDto.getUsername());
    }
}