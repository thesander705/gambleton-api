package com.gambleton.dataAccessLayer;

import com.gambleton.dataAccessLayer.abstraction.MatchContext;
import com.gambleton.models.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
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
        Match match;
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        match = session.get(Match.class, id);

        session.getTransaction().commit();

        return match;
    }

    @Override
    public List<Match> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        ArrayList<Match> matches = (ArrayList<Match>) session.createQuery("from Match").list();

        session.getTransaction().commit();
        return matches;
    }

    @Override
    public void update(Match entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Match toUpdate = session.get(Match.class, entity.getId());
        toUpdate.setTitle(entity.getTitle());
        toUpdate.setDescription(entity.getDescription());

        session.update(toUpdate);
        session.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Match toDelete = session.get(Match.class, id);
        session.delete(toDelete);
        session.getTransaction().commit();
    }
}
