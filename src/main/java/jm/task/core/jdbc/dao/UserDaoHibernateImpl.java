package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction trans = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String SQL = "CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT not null AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255)," +
                    "lastName VARCHAR(255)," +
                    "age TINYINT)";
            Query query = session.createSQLQuery(SQL).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            String SQL = "DROP TABLE IF EXISTS users";
            Query query = session.createSQLQuery(SQL).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            trans = session.beginTransaction();
            session.save(user);
            trans.commit();

        } catch (Exception e) {
            trans.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            trans = session.beginTransaction();
            String HQL = "DELETE User where id = :id";
            Query query = session.createQuery(HQL);
            query.setParameter("id", id).executeUpdate();
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        // return (List<User>) Util.getSessionFactory().openSession().createQuery("From User").list();
        List<User> user = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            trans = session.beginTransaction();
            String SQL = "From User";
            user = session.createQuery(SQL, User.class).list();
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            trans = session.beginTransaction();
            String HQL = "TRUNCATE TABLE my_db_test.users";
            Query query = session.createSQLQuery(HQL).addEntity(User.class);
            query.executeUpdate();
            trans.commit();
            session.close();

        } catch (Exception e) {
            trans.rollback();
            throw new RuntimeException(e);
        }
    }
}