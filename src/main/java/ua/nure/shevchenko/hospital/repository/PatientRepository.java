package ua.nure.shevchenko.hospital.repository;

import ua.nure.shevchenko.hospital.model.Doctor;
import ua.nure.shevchenko.hospital.model.Patient;

import java.sql.SQLException;
import java.util.List;

public interface PatientRepository {
    List<Patient> getAllPatients() throws SQLException;
    List<Patient> getPatients(Doctor doctor) throws SQLException;
    boolean insert(Patient patient) throws SQLException;

}
