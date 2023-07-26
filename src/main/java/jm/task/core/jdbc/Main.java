package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

public class Main {

    private static UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();

    private static final User user1 = new User("Name1", "LastName1", (byte) 31);
    private static final User user2 = new User("Name2", "LastName2", (byte) 45);
    private static final User user3 = new User("Name3", "LastName3", (byte) 19);
    private static final User user4 = new User("Name4", "LastName4", (byte) 51);

    public static void main(String[] args) {

        userDaoHibernate.createUsersTable();

        userDaoHibernate.saveUser(user1.getName(), user1.getLastName(), user1.getAge());

        userDaoHibernate.saveUser(user2.getName(), user2.getLastName(), user2.getAge());

        userDaoHibernate.saveUser(user3.getName(), user3.getLastName(), user3.getAge());

        userDaoHibernate.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        userDaoHibernate.removeUserById(1);

        userDaoHibernate.getAllUsers();

        userDaoHibernate.cleanUsersTable();

        userDaoHibernate.dropUsersTable();

    }
}
