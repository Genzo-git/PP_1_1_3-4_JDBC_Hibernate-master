package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Util {
    // реализуйте настройку соеденения с БД
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/mydbtest";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "rootroot";


    //В класс Util должна быть добавлена конфигурация для Hibernate ( рядом с JDBC), без использования xml.

    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {
        //if (sessionFactory == null) {
        try {
            Configuration configuration = new Configuration()
                    //Properties properties = new Properties();
                    .setProperty("hibernate.connection.driver_class", DB_DRIVER)
                    .setProperty("hibernate.connection.url", DB_URL)
                    .setProperty("hibernate.connection.username", DB_USERNAME)
                    .setProperty("hibernate.connection.password", DB_PASSWORD)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .addAnnotatedClass(User.class);
                    //.setProperty("hibernate.c3p0.min_size", "5")
                    ///.setProperty("hibernate.c3p0.max_size", "200")
                    //.setProperty("hibernate.c3p0.max_statements", "200");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}

// Hibernate configuration above

// JDBC configuration below
/*
    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection OK");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return null;

    }

 */
