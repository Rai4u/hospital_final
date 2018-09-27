package ua.nure.shevchenko.hospital.repository;

import org.apache.log4j.Logger;
import ua.nure.shevchenko.hospital.Context;
import ua.nure.shevchenko.hospital.database.TransactionManager;
import ua.nure.shevchenko.hospital.model.Doctor;
import ua.nure.shevchenko.hospital.model.Patient;
import ua.nure.shevchenko.hospital.model.Role;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class PatientRepositoryImpl implements PatientRepository {
    private static final Logger LOGGER = Logger.getLogger(PatientRepositoryImpl.class);
    private static final String INSERT_PATIENT = "INSERT INTO public.patience(" +
            "user_id, hosp_card_id, birth_day) " +
            "VALUES (?, ?, ?);";
    private TransactionManager txManager = Context.get(TransactionManager.class);

    @Override
    public List<Patient> getAllPatients() throws SQLException {
        String sql = "Select user_name, user_surname, user_email, user_password, " +
                "role_name, birth_day, job_name " +
                "From users, patience where users.user_id = patience.user_id;";
        List<Patient> patients = new ArrayList<>();

        Connection conn = txManager.getConnection();

        try (Statement statement = conn.createStatement();
             ResultSet set = statement.executeQuery(sql)) {
            while (set.next()) {
                patients.add(fillPatientData(set));
            }
            return patients;
        } finally {
            closeConnection(conn);
        }
    }


    @Override
    public List<Patient> getPatients(Doctor doctor) throws SQLException {

        return null;
    }

    @Override
    public boolean insert(Patient patient) throws SQLException {
        Connection conn = txManager.getConnection();
        try (PreparedStatement statement = conn.prepareStatement(INSERT_PATIENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, patient.getUserId());
            statement.setInt(2, patient.getCard().getCardId());
            statement.setDate(3, Date.valueOf(patient.getBirthDay()));

            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                patient.setPatientId(keys.getInt(1));
            }

            return statement.executeUpdate() != 0;
        } finally {
            closeConnection(conn);
        }
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection.getAutoCommit()) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    private Patient fillPatientData(ResultSet data) throws SQLException {
        Patient patient = new Patient();
        patient.setName(data.getString(1));
        patient.setSurname(data.getString(2));
        patient.setEmail(data.getString(3));
        patient.setPassword(data.getString(4));
        patient.setRole(Role.valueOf(data.getString(5)));
        patient.setBirthDay(data.getDate(6).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

//        patient.setCard(data.getString(7)));
        return patient;
    }
}
