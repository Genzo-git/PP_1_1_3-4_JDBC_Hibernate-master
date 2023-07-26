package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Util {

    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "rootroot";

    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {

        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", DB_DRIVER)
                    .setProperty("hibernate.connection.url", DB_URL)
                    .setProperty("hibernate.connection.username", DB_USERNAME)
                    .setProperty("hibernate.connection.password", DB_PASSWORD)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}