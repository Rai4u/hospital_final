package ua.nure.shevchenko.hospital.service;

import ua.nure.shevchenko.hospital.model.Doctor;
import ua.nure.shevchenko.hospital.model.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getPatients(Doctor doctor);
    List<Patient> getAllPatients();
}
