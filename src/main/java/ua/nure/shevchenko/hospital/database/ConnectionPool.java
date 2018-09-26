package ua.nure.shevchenko.hospital.database;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import ua.nure.shevchenko.hospital.exception.InitialisationException;
import ua.nure.shevchenko.hospital.properties.DataBaseConfig;

import javax.sql.DataSource;

public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private static final DataBaseConfig CONFIG = ConfigFactory.create(DataBaseConfig.class);

    private DataSource dataSource = setUpPool();

    public static DataSource setUpPool() {
        try {
            Class.forName(CONFIG.driver());
        } catch (ClassNotFoundException e) {
            LOGGER.error("Data driver not found: " + CONFIG.driver());
            throw new InitialisationException(e);
        }

        GenericObjectPool gPool = new GenericObjectPool();
        gPool.setMaxActive(CONFIG.poolSize());

        ConnectionFactory cf = new DriverManagerConnectionFactory(CONFIG.url(), CONFIG.user(), CONFIG.password());

        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool,
                null, null, false, true);
        return new PoolingDataSource(gPool);
    }

}