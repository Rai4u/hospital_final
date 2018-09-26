package ua.nure.shevchenko.hospital.repository;

import ua.nure.shevchenko.hospital.model.Patience;

import java.util.List;

public interface PatienceRepository {
    List<Patience> getAllPatients();
}
