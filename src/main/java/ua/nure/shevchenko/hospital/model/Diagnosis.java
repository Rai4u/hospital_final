package ua.nure.shevchenko.hospital.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Diagnosis {
    private int diagnosisId;
    private String diagnosisName;
    private HospitalCard  userCard;
    private LocalDate openingDate;
    private LocalDate releaseDate;
    private Doctor doctor;
}
