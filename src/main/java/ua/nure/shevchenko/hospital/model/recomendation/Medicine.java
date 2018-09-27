package ua.nure.shevchenko.hospital.model.recomendation;

import lombok.Getter;
import lombok.Setter;
import ua.nure.shevchenko.hospital.model.Diagnosis;

@Getter
@Setter
public class Medicine {
    private String medicineName;
    private Diagnosis diagnosis;
}
