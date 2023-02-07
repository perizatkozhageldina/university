package ua.foxminded.university.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("ua.foxminded.university")
@PropertySource("classpath:application.properties")
public class AppConfig {
 
    @Autowired
    public Environment environment;

    private static final String URL = "url";
    private static final String USER = "dbuser";
    private static final String DRIVER = "driver";
    private static final String PASSWORD = "dbpassword";

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(environment.getProperty(URL));
        driverManagerDataSource.setUsername(environment.getProperty(USER));
        driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
        driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
        return driverManagerDataSource;
    }
}