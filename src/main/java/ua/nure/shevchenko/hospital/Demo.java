package ua.nure.shevchenko.hospital;


import ua.nure.shevchenko.hospital.model.User;
import ua.nure.shevchenko.hospital.repository.DoctorRepository;
import ua.nure.shevchenko.hospital.repository.DoctorRepositoryImpl;
import ua.nure.shevchenko.hospital.repository.UserRepository;
import ua.nure.shevchenko.hospital.repository.UserRepositoryImpl;
import ua.nure.shevchenko.hospital.service.UserService;
import ua.nure.shevchenko.hospital.service.UserServiceImpl;

import java.util.List;
import java.util.Optional;

public class Demo {

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl();
        DoctorRepository doctorRepository = new DoctorRepositoryImpl();
        UserService service = new UserServiceImpl(userRepository, doctorRepository);


    }
}
