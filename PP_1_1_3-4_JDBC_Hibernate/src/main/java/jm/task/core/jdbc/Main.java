package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.saveUser("Иван", "Иванов", (byte) 25);
        userDao.saveUser("Петр", "Петров", (byte) 26);
        userDao.saveUser("Константин", "Константинов", (byte) 27);
        userDao.saveUser("Степан", "Степанов", (byte) 28);
        List<User> users = userDao.getAllUsers();
        System.out.println(users);
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
