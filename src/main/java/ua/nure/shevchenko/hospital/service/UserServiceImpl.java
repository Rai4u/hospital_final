package ua.nure.shevchenko.hospital.service;

import org.apache.log4j.Logger;
import ua.nure.shevchenko.hospital.Context;
import ua.nure.shevchenko.hospital.database.TransactionManager;
import ua.nure.shevchenko.hospital.model.Doctor;
import ua.nure.shevchenko.hospital.model.Patient;
import ua.nure.shevchenko.hospital.model.User;
import ua.nure.shevchenko.hospital.repository.DoctorRepository;
import ua.nure.shevchenko.hospital.repository.PatienceRepository;
import ua.nure.shevchenko.hospital.repository.UserRepository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private DoctorRepository doctorRepository;
    private PatienceRepository patienceRepository;
    private TransactionManager txManager;

    public UserServiceImpl() {
        userRepository = Context.get(UserRepository.class);
        doctorRepository = Context.get(DoctorRepository.class);
        txManager = Context.get(TransactionManager.class);
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
    public int saveUser(User user) {
        try {
            return userRepository.insert(user);
        } catch (SQLException e) {
            LOGGER.error("in user insert");
        }
        return -1;
    }

    @Override
    public Optional<User> getBy(String email) {
        return userRepository.getBy(email);
    }

    @Override
    public List<User> getBy(String name, String surname) {
        return userRepository.getBy(name, surname);
    }

    @Override
    public List<Patient> getAllPatience() {
        return patienceRepository.getAllPatients();
    }

    @Override
    public List<Doctor> getAllDoctor() {
        try {
            return doctorRepository.getAllDoctors();
        } catch (SQLException e) {
            LOGGER.error("during doctors in DB: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean addDoctor(Doctor doctor) {
        boolean insert = false;
        try {
            txManager.beginTransaction();
            Optional<User> user = findUser(doctor);
            if (user.isPresent()) {
                doctor.setUserId(user.get().getUserId());
            } else {
                int userId = userRepository.insert(doctor);
                doctor.setUserId(userId);
            }
            insert = doctorRepository.insert(doctor);
            LOGGER.info("Added new doctor");
            txManager.commit();
        } catch (SQLException e) {
            LOGGER.error("doctor doesn`t insert:", e);
            txManager.rollback();
        }
        return insert;
    }


}
