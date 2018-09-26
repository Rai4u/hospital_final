package ua.nure.shevchenko.hospital.repository;

import org.apache.log4j.Logger;
import ua.nure.shevchenko.hospital.database.ConnectionPool;
import ua.nure.shevchenko.hospital.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepositoryImpl implements DoctorRepository {
    private static final Logger LOGGER = Logger.getLogger(DoctorRepositoryImpl.class);
    private static final String INSERT_DOCTOR = "INSERT INTO doctor(" +
            "user_id, patiencecount, job_name) " +
            "VALUES (?, ?, ?);";

    @Override
    public List<Doctor> getAllDoctors() {
        String sql = "Select user_name, user_surname, user_email, user_password, " +
                "role_name, patiencecount, job_name " +
                "From users, doctor where users.user_id = doctor.user_id;";
        return searchDoctors(sql);
    }

    @Override
    public boolean insert(Doctor doctor) {
        boolean check = false;
        try (Connection conn = ConnectionPool.INSTANCE.getDataBaseConnection();
             PreparedStatement statement = conn.prepareStatement(INSERT_DOCTOR)) {
            statement.setInt(1, doctor.getUserId());
            statement.setInt(2, doctor.getPatienceCount());
            statement.setString(3, doctor.getJob().name());
            check = statement.executeUpdate() != 0;
            return check;
        } catch (SQLException ex) {
            LOGGER.error("Error in insert doctor sql: " + ex.getMessage());
        }
        return check;
    }

    @Override
    public List<Patience> getPatients() {
        return null;
    }


    //My functions
    private List<Doctor> searchDoctors(String sql) {
        List<Doctor> list = new ArrayList<>();

        try (Connection conn = ConnectionPool.INSTANCE.getDataBaseConnection();
             Statement statement = conn.createStatement();
             ResultSet set = statement.executeQuery(sql)) {
            while (set.next()) {
                list.add(fillDoctorData(set));
            }
        } catch (SQLException readData) {
            LOGGER.error("Error in searchDoctor sql: " + readData.getMessage());
        }

        return list;
    }

    private Doctor fillDoctorData(ResultSet data) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setName(data.getString(1));
        doctor.setSurname(data.getString(2));
        doctor.setEmail(data.getString(3));
        doctor.setPassword(data.getString(4));
        doctor.setRole(Role.valueOf(data.getString(5)));
        doctor.setPatienceCount(data.getInt(6));
        doctor.setJob(Job.valueOf(data.getString(7)));
        return doctor;
    }
}
