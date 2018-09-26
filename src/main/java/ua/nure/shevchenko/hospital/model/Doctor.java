package ua.nure.shevchenko.hospital.model;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class Doctor extends User {
    private int patienceCount;
    private Job job;

}
