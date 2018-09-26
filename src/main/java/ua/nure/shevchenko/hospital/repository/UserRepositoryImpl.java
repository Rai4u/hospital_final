package ua.nure.shevchenko.hospital.repository;

import org.apache.log4j.Logger;
import ua.nure.shevchenko.hospital.Context;
import ua.nure.shevchenko.hospital.database.ConnectionPool;
import ua.nure.shevchenko.hospital.database.TransactionManager;
import ua.nure.shevchenko.hospital.model.Role;
import ua.nure.shevchenko.hospital.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private static final Logger LOGGER = Logger.getLogger(UserRepositoryImpl.class);
    private static final String INSERT_USER = "INSERT INTO users(" +
            "user_name, user_surname, user_email, user_password, role_name) " +
            "VALUES (?, ?, ?, ?, ?)";
    private TransactionManager txManager = Context.get(TransactionManager.class);

    @Override
    public Optional<User> find(User user) {
        String sql = "Select * from users where user_email='" + user.getEmail() + "';";
        return get(sql);
    }

    @Override
    public int insert(User user) throws SQLException {
        int userId = -1;
        Connection connection = txManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().name());
            closeConnection(connection);

            ResultSet keys = statement.getGeneratedKeys();
            if(keys.next()){
                userId = keys.getInt(1);
            }
            return userId;
        }
    }

    @Override
    public Optional<User> getBy(String email) {
        String sql = "Select * from users where user_email='" + email + "';";
        return get(sql);
    }

    @Override
    public List<User> getBy(String name, String surname) {
        String sql = "Select * from users where user_name ='" + name + "' AND user_surname='" + surname + "';";
        return  searchUsers(sql);
    }


    private List<User> searchUsers(String sql){
        List<User> list = new ArrayList<>();

        try (Connection conn = txManager.getConnection();
             Statement statement = conn.createStatement();
             ResultSet set = statement.executeQuery(sql)){
            while(set.next()){
                list.add(fillUserData(set));
            }
        } catch (SQLException readData){
            LOGGER.error("Error in searchUser sql: " + readData.getMessage());
        }

        return list;
    }

    private Optional<User> get(String sql){
        Optional<User> optional = Optional.empty();
        try (Connection conn = txManager.getConnection();
             Statement statement = conn.createStatement();
             ResultSet set = statement.executeQuery(sql)) {

            if (set.next()) {
                optional = Optional.of(fillUserData(set));
            } else {
                optional = Optional.empty();
            }

        } catch (SQLException ex) {
            LOGGER.error("Error in sql: " + ex.getMessage());
        }

        return optional;
    }

    private User fillUserData(ResultSet data) throws SQLException {
        User user = new User();
        user.setUserId(data.getInt(1));
        user.setName(data.getString(2));
        user.setSurname(data.getString(3));
        user.setEmail(data.getString(4));
        user.setPassword(data.getString(5));
        user.setRole(Role.valueOf(data.getString(6)));
        return user;
    }

    private void closeConnection(Connection connection){
        try {
            if(connection.getAutoCommit()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
