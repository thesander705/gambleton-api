package com.gambleton.repository;

import com.gambleton.models.User;
import com.gambleton.repository.abstraction.UserContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserHibernateContext implements UserContext {
    private SessionFactory sessionFactory;

    public UserHibernateContext() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public User getByCredentials(String username, String password) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Query query = session.createQuery("from User where username=:username and password=:password");
        query.setParameter("username", username);
        query.setParameter("password", password);

        List users = query.list();

        return (User) users.get(0);
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


        return session.get(User.class, id);

    }

    @Override
    public List<User> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        ArrayList<User> users = (ArrayList<User>) session.createQuery("from User").list();
        return users;
    }

    @Override
    public void update(User entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        User toUpdate = session.get(User.class, entity.getId());
        toUpdate.setRole(entity.getRole());

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
