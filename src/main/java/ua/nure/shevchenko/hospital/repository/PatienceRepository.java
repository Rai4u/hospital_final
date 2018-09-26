package ua.nure.shevchenko.hospital.repository;

import ua.nure.shevchenko.hospital.model.Doctor;
import ua.nure.shevchenko.hospital.model.Patient;

import java.util.List;

public interface PatienceRepository {
    List<Patient> getAllPatients();
    List<Patient> getPatients(Doctor doctor);

}
