package ua.nure.shevchenko.hospital.service.implement;

import org.apache.log4j.Logger;
import ua.nure.shevchenko.hospital.Context;
import ua.nure.shevchenko.hospital.database.TransactionManager;
import ua.nure.shevchenko.hospital.model.Doctor;
import ua.nure.shevchenko.hospital.model.User;
import ua.nure.shevchenko.hospital.repository.DoctorRepository;
import ua.nure.shevchenko.hospital.repository.UserRepository;
import ua.nure.shevchenko.hospital.service.DoctorService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DoctorServiceImpl implements DoctorService {
    private static final Logger LOGGER = Logger.getLogger(DoctorServiceImpl.class);
    private UserRepository userRepository;
    private DoctorRepository doctorRepository;
    private TransactionManager txManager = Context.get(TransactionManager.class);

    public DoctorServiceImpl() {
        doctorRepository = Context.get(DoctorRepository.class);
        userRepository = Context.get(UserRepository.class);
    }


    @Override
    public List<Doctor> getDoctors() {
        try {
            return doctorRepository.getAllDoctors();
        } catch (SQLException e) {
            LOGGER.error("error with doctor find");
        }
        return Collections.emptyList();
    }


    @Override
    public boolean addDoctor(Doctor doctor) {
        try {
            txManager.beginTransaction();
            Optional<User> user = userRepository.find(doctor);
            if (user.isPresent()) {
                doctor.setUserId(user.get().getUserId());
            } else {
                int userId = userRepository.insert(doctor);
                doctor.setUserId(userId);
            }
            doctorRepository.insert(doctor);
            LOGGER.info("Added new doctor");
            txManager.commit();
            return doctorRepository.insert(doctor);
        } catch (SQLException e) {
            LOGGER.error("doctor doesn`t insert:", e);
            txManager.rollback();
        }
        return false;
    }


}
