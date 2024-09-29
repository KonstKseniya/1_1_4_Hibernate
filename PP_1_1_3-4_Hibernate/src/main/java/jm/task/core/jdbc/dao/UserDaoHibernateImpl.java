package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = Util.getSessionFactory();

    private static final String CREATE_USERS_TABLE = "CREATE TABLE user (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) , last_name VARCHAR(255) , age INT)";
    private static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS user";

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(CREATE_USERS_TABLE).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица пользователей создана");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании таблицы пользователей", e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(DROP_USERS_TABLE).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица пользователей удалена");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении таблицы пользователей", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            session.getTransaction().commit();
            System.out.println("User с именем — " + name + " добавлен в базу данных");
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            throw new RuntimeException("Ошибка при сохранении пользователя", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            if (session.get(User.class, id) != null) {
                session.delete(session.get(User.class, id));
                System.out.println("User с id - " + id + " удален из базы данных");
            } else {
                System.out.println("User с id - " + id + " не найден в базе данных");
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            throw new RuntimeException("Ошибка при удалении пользователя по id", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            users = session.createQuery("SELECT a FROM User a", User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении списка пользователей из базы данных");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица пользователей очищена");
        } catch (Exception e) {
            sessionFactory.getCurrentSession().getTransaction().rollback();
            throw new RuntimeException("Ошибка при очистке таблицы пользователей", e);
        }
    }
}
