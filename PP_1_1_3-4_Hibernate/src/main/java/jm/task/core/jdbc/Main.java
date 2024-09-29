package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Иван", "Иванов", (byte) 25);
        userService.saveUser("Петр", "Петров", (byte) 26);
        userService.saveUser("Константин", "Константинов", (byte) 27);
        userService.saveUser("Степан", "Степанов", (byte) 28);

        List<User> users = userService.getAllUsers();
        System.out.println(users);

        userService.cleanUsersTable();

        userService.dropUsersTable();


    }
}
