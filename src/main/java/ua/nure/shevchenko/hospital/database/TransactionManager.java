package ua.nure.shevchenko.hospital.database;

import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class TransactionManager {
    private static final Logger LOGGER = Logger.getLogger(TransactionManager.class);
    private DataSource dataSource;
    private ThreadLocal<Connection> connection = new ThreadLocal<>();

    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = this.connection.get();
        if (Objects.isNull(connection)) {
            return dataSource.getConnection();
        }
        return connection;
    }

    public void beginTransaction() {
        try {
            if (Objects.isNull(connection.get())) {
                Connection conn = dataSource.getConnection();
                conn.setAutoCommit(false);
                connection.set(conn);
                LOGGER.debug("Begin transaction");
            }
        } catch (SQLException e) {
            LOGGER.error("in transaction begin: ", e);
        }
    }

    public void commit() {
        try {
            Connection conn = connection.get();
            if (Objects.nonNull(conn)) {
                conn.commit();
                conn.setAutoCommit(true);
                conn.close();
                LOGGER.debug("Commit transaction");
            }
        } catch (SQLException e) {
            LOGGER.error("in transaction commit: ", e);
        } finally {
            connection.remove();
        }
    }

    public void rollback() {
        try {
            Connection conn = connection.get();
            if (Objects.nonNull(conn)) {
                conn.rollback();
                conn.setAutoCommit(true);
                conn.close();
                LOGGER.debug("Rollback transaction");
            }
        } catch (SQLException e) {
            LOGGER.error("in transaction rollback: ", e);
        }finally {
            connection.remove();
        }
    }

}
