package ua.nure.shevchenko.hospital.repository;

import ua.nure.shevchenko.hospital.model.Doctor;
import ua.nure.shevchenko.hospital.model.Patience;

import java.util.List;

public interface DoctorRepository {
    List<Doctor> getAllDoctors();
    boolean insert(Doctor doctor);
    List<Patience> getPatients();

}
