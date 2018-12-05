package com.gambleton.dataAccessLayer;

import com.gambleton.dataAccessLayer.abstraction.MatchContext;
import com.gambleton.models.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class MatchHibernateContext implements MatchContext {
    private final SessionFactory sessionFactory;

    public MatchHibernateContext() {
        this("hibernate.cfg.xml");
    }

    MatchHibernateContext(String filePath){
        try {
            this.sessionFactory = new Configuration().configure(filePath).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    MatchHibernateContext(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Match entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(entity);

        session.getTransaction().commit();
    }

    @Override
    public Match get(int id) {
        return null;
    }

    @Override
    public List<Match> getAll() {
        return null;
    }

    @Override
    public void update(Match entity) {

    }

    @Override
    public void delete(int id) {

    }
}
