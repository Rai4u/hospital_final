package ua.nure.shevchenko.hospital.repository;

import ua.nure.shevchenko.hospital.model.Doctor;

import java.sql.SQLException;
import java.util.List;

public interface DoctorRepository {
    List<Doctor> getAllDoctors() throws SQLException;
    boolean insert(Doctor doctor) throws SQLException;

}
