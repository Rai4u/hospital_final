package ua.nure.shevchenko.hospital.repository;

import ua.nure.shevchenko.hospital.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> find(User user);
    boolean insert(User user);
    Optional<User> getBy(String email);
    List<User> getBy(String name, String surname);
}
