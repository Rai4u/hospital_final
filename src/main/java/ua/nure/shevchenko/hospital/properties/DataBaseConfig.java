package ua.nure.shevchenko.hospital.properties;

import org.aeonbits.owner.Config;

@Config.Sources("file:DataBaseConfig.properties")
public interface DataBaseConfig extends Config {
    String driver();
    String url();
    String user();
    String password();
    int poolSize();
}
