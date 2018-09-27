package ua.nure.shevchenko.hospital.service;

import ua.nure.shevchenko.hospital.model.Doctor;
import ua.nure.shevchenko.hospital.model.Patient;
import ua.nure.shevchenko.hospital.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean isUserExist(User user);
    Optional<User> findUser(User user);
    int saveUser(User user);
    Optional<User> getBy(String email);
    List<User> getBy(String name, String surname);

}
