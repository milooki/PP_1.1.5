package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao uD = new UserDaoHibernateImpl();

    public void createUsersTable() {
        uD.createUsersTable();
    }

    public void dropUsersTable() {
        uD.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        uD.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        uD.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return uD.getAllUsers();
    }

    public void cleanUsersTable() {
        uD.cleanUsersTable();
    }
}
