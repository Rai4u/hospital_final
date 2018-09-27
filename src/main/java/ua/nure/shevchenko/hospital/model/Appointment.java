package ua.nure.shevchenko.hospital.model;

import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime meetingDate;
}
