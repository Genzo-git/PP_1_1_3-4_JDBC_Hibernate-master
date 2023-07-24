package jm.task.core.jdbc.dao;

import jakarta.persistence.criteria.CriteriaQuery;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


// Методы создания и удаления таблицы пользователей в классе UserHibernateDaoImpl
// должны быть реализованы с помощью SQL.


    //Метод создания таблицы пользователей в классе UserHibernateDaoImpl должен быть реализован с помощью SQL
    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(250) NOT NULL, lastName VARCHAR(250) DEFAULT NULL, " +
                    "age TINYINT DEFAULT NULL)";
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println("Таблица НЕ создана");
        }
    }

    //Метод удаления таблицы пользователей в классе UserHibernateDaoImpl должен быть реализован с помощью SQL
    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            session.createNativeQuery(sql).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println("Таблица НЕ удалена");
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User с именем " + name + " добавлен в БД");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println("User с именем " + name + " НЕ добавлен в БД");
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.println("User с id №" + id + " удален");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            //e.printStackTrace();
            System.out.println("User с id №" + id + " НЕ удален");
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> list = null;
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            transaction = session.beginTransaction();
            list = session.createQuery(criteriaQuery).getResultList();
            session.getTransaction().commit();
            System.out.println(list);
        } catch (HibernateException e) {
            //e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE mydbtest.users;").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Table is truncated");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            //e.printStackTrace();
        }
    }
}
