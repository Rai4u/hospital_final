package ua.nure.shevchenko.hospital.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int userId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Role role;

    @Override
    public String toString() {
        return String.valueOf(userId) + " " + name + " " + surname + " " + email + " " + password  + " " + role.toString();
    }
}
