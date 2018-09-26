package ua.nure.shevchenko.hospital.database;

import javax.sql.DataSource;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import ua.nure.shevchenko.hospital.exception.InitialisationException;
import ua.nure.shevchenko.hospital.properties.DataBaseConfig;

import java.sql.Connection;
import java.sql.SQLException;

public enum ConnectionPool {
    INSTANCE;

    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private DataBaseConfig config = ConfigFactory.create(DataBaseConfig.class);
    private DataSource dataSource = setUpPool();

    private DataSource setUpPool() {
        try {
            Class.forName(config.driver());
        } catch (ClassNotFoundException e) {
            LOGGER.error("Data driver not found: " + config.driver());
            throw new InitialisationException(e);
        }

        GenericObjectPool gPool = new GenericObjectPool();
        gPool.setMaxActive(config.poolSize());

        ConnectionFactory cf = new DriverManagerConnectionFactory(config.url(), config.user(), config.password());

        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool,
                null, null, false, true);
        return new PoolingDataSource(gPool);
    }

    public Connection getDataBaseConnection() throws SQLException {
        return dataSource.getConnection();
    }

}