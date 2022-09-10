package ru.proj.sharedubki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.proj.sharedubki.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
