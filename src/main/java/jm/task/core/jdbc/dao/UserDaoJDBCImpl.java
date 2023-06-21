package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    public UserDaoJDBCImpl() {

    }

    Connection connection = getConnection();

    public void createUsersTable() throws SQLException {

        String sql = """
                alter table users
                        add id int auto_increment;

                        alter table users
                        add name varchar(45) null;

                        alter table users
                        add lastName varchar(45) null;

                        alter table users
                        add age tinyint null;""";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }

    public void dropUsersTable() throws SQLException {
        String sql = "DROP TABLE USERS";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }


    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
            preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    public void removeUserById(long id) throws SQLException {

        String sql = "DELETE FROM USERS WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM USERS";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        // попробую проще
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE USERS");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

    }
}
