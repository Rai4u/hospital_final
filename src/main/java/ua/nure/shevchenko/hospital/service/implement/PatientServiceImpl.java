package ua.nure.shevchenko.hospital.service.implement;

import org.apache.log4j.Logger;
import ua.nure.shevchenko.hospital.Context;
import ua.nure.shevchenko.hospital.model.Doctor;
import ua.nure.shevchenko.hospital.model.Patient;
import ua.nure.shevchenko.hospital.repository.PatientRepository;
import ua.nure.shevchenko.hospital.service.PatientService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class PatientServiceImpl implements PatientService {
    private static final Logger LOGGER = Logger.getLogger(PatientServiceImpl.class);
    private PatientRepository patientRepository;

    public PatientServiceImpl() {
        patientRepository = Context.get(PatientRepository.class);
    }


    @Override
    public List<Patient> getPatients(Doctor doctor) {
        return null;
    }

    @Override
    public List<Patient> getAllPatients() {
        try {
            return patientRepository.getAllPatients();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();
    }
}
