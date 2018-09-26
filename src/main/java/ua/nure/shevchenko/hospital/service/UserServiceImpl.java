package ua.nure.shevchenko.hospital.service;

import org.apache.log4j.Logger;
import ua.nure.shevchenko.hospital.model.*;
import ua.nure.shevchenko.hospital.repository.DoctorRepository;
import ua.nure.shevchenko.hospital.repository.PatienceRepository;
import ua.nure.shevchenko.hospital.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private DoctorRepository doctorRepository;
    private PatienceRepository patienceRepository;


    public UserServiceImpl(UserRepository userRepository, DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public boolean isUserExist(User user) {
        return userRepository.find(user).isPresent();
    }

    @Override
    public Optional<User> findUser(User user) {
        return userRepository.find(user);
    }

    @Override
    public boolean saveUser(User user) {
        return userRepository.insert(user);
    }

    @Override
    public Optional<User> getBy(String email) {
        return userRepository.getBy(email);
    }

    @Override
    public List<User> getBy(String name, String surname) {
        return userRepository.getBy(name,surname);
    }

    @Override
    public List<Patience> getAllPatience() {
        return patienceRepository.getAllPatients();
    }

    @Override
    public List<Doctor> getAllDoctor() {
        return doctorRepository.getAllDoctors();
    }

    @Override
    public boolean addNewDoctor(Doctor doctor) {
        if (isUserExist(doctor)) {
            doctor.setUserId(getUserId(doctor));
            doctor.setRole(Role.WORKER);
            return doctorRepository.insert(doctor);
        } else {
            userRepository.insert(doctor);
            doctor.setUserId(getUserId(doctor));
            return doctorRepository.insert(doctor);
        }

    }

    private int getUserId(User user){
        return findUser(user).get().getUserId();
    }

}
