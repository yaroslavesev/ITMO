package by.yaroslavesev.backend.services;
import by.yaroslavesev.backend.DTO.AuthDTO;
import by.yaroslavesev.backend.models.User;
import by.yaroslavesev.backend.repositories.UserRepository;
import by.yaroslavesev.backend.utils.AuthUtils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
@ApplicationScoped
public class AuthService {

    @Inject
    private UserRepository userRepository;

    public void register(AuthDTO authDTO) {
        if (userRepository.userExists(authDTO.getUsername())){
            throw new IllegalArgumentException("User already exists");
        }
        User user = new User();
        user.setUsername(authDTO.getUsername());
        user.setHashedPassword(AuthUtils.hashPassword(authDTO.getPassword()));
        userRepository.registerUser(user);
    }

    public String login(AuthDTO authDTO) {
        User foundUser = userRepository.findUser(authDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!AuthUtils.verifyPassword(authDTO.getPassword(), foundUser.getHashedPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        return AuthUtils.generateToken(foundUser.getUsername());
    }
}
