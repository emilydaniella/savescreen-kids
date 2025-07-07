package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseConnection {

    private static HikariDataSource dataSource;

    static{
        Config config = new Config("config/config.properties");

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getProperty("db.url"));
        hikariConfig.setUsername(config.getProperty("db.user"));
        hikariConfig.setPassword(config.getProperty("db.password"));
        
        String maxPoolSize = config.getProperty("db.maxPoolSize");

        if(maxPoolSize != null){
            hikariConfig.setMaximumPoolSize(Integer.parseInt(maxPoolSize));
        }

        dataSource = new HikariDataSource(hikariConfig);
    }

    public static Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }
}