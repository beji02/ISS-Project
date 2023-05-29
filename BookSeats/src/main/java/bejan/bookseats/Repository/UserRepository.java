package bejan.bookseats.Repository;

import bejan.bookseats.Domain.User;
import bejan.bookseats.Utils.Utils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class UserRepository {
    private SessionFactory sessionFactory;

    public UserRepository() {
        sessionFactory = Utils.getInstanceOfSessionFactory();
        System.out.println("User repo initialized.");
    }

    public User findOne(String username, String password) {
        User user = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                user = session.createQuery("from User where username = :username and password = :password", User.class)
                        .setParameter("username", username)
                        .setParameter("password", password)
                        .uniqueResult();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }

        return user;
    }

    public User findUser(String username) {
        User user = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                user = session.createQuery("from User where username = :username", User.class)
                        .setParameter("username", username)
                        .uniqueResult();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }

        return user;
    }

    public User add(String username, String mail, String name, String password) {
        User user = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                user = new User(username, mail, name, password);
                session.save(user);
                tx.commit();
                user = findOne(username, password);
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                user = null;
            }
        }

        return user;
    }
}
