package com.gambleton.dataAccessLayer;

import com.gambleton.dataAccessLayer.abstraction.UserContext;
import com.gambleton.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserHibernateContext implements UserContext {
    private SessionFactory sessionFactory;

    public UserHibernateContext() {
        this("hibernate.cfg.xml");
    }

    UserHibernateContext(String filePath){
        try {
            sessionFactory = new Configuration().configure(filePath).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    UserHibernateContext(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User getByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query query = session.createQuery("from User where username=:username");
        query.setParameter("username", username);

        List users = query.list();

        if (users.size() < 1) {
            return null;
        }

        User user = (User) users.get(0);
        session.getTransaction().commit();

        return user;
    }

    @Override
    public User getByAuthToken(String authToken) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query query = session.createQuery("from User where authToken=:authToken");
        query.setParameter("authToken", authToken);

        List users = query.list();

        if (users.size() < 1) {
            return null;
        }

        User user = (User) users.get(0);
        session.getTransaction().commit();

        return user;
    }

    @Override
    public void create(User entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(entity);

        session.getTransaction().commit();
    }

    @Override
    public User get(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();


        User user = session.get(User.class, id);
        session.getTransaction().commit();

        return user;
    }

    @Override
    public List<User> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        ArrayList<User> users = (ArrayList<User>) session.createQuery("from User").list();

        session.getTransaction().commit();
        return users;
    }

    @Override
    public void update(User entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User toUpdate = session.get(User.class, entity.getId());
        toUpdate.setRole(entity.getRole());
        toUpdate.setUsername(entity.getUsername());
        toUpdate.setPassword(entity.getPassword());
        toUpdate.setMoney(entity.getMoney());

        session.update(toUpdate);
        session.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User toDelete = session.get(User.class, id);
        session.delete(toDelete);
        session.getTransaction().commit();
    }
}
