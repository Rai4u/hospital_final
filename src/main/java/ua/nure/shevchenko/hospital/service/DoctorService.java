package ua.nure.shevchenko.hospital.service;

import ua.nure.shevchenko.hospital.model.Doctor;

import java.util.List;

public interface DoctorService {
    List<Doctor> getDoctors();
    boolean addDoctor(Doctor doctor);
}
