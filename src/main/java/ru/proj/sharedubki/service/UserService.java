package ru.proj.sharedubki.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.proj.sharedubki.enums.Role;
import ru.proj.sharedubki.model.User;
import ru.proj.sharedubki.repository.UserRepository;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createUser (User user) {
        String email = user.getEmail();
        if (userRepository.findByEmail(email) != null)
            return false;
        user.setActive(true);
        // пароль
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
        log.info("saving new user with email: {}", email);
        return true;
    }


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user != null) {
            if(user.isActive()) {
                user.setActive(false);
                log.info("user with id = {}; email = {} was banned", user.getId(), user.getEmail());
            }
            else {
                user.setActive(true);
                log.info("user with id = {}; email = {} was unbanned", user.getId(), user.getEmail());
            }
            userRepository.save(user);
        }
    }

    public void changeUserRoles(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if(roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }
}
