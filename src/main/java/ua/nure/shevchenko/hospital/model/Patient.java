package ua.nure.shevchenko.hospital.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Patient extends User{
    private int patientId;
    private LocalDate birthDay;
    private HospitalCard card;
}
