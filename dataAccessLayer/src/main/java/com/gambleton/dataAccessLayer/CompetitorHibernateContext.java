package com.gambleton.dataAccessLayer;

import com.gambleton.dataAccessLayer.abstraction.CompetitorContext;
import com.gambleton.models.Competitor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class CompetitorHibernateContext implements CompetitorContext {

    private final SessionFactory sessionFactory;

    public CompetitorHibernateContext() {
        this("hibernate.cfg.xml");
    }

    CompetitorHibernateContext(String filePath){
        try {
            this.sessionFactory = new Configuration().configure(filePath).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    CompetitorHibernateContext(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void create(Competitor entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(entity);

        session.getTransaction().commit();
    }

    @Override
    public Competitor get(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();


        Competitor competitor = session.get(Competitor.class, id);
        session.getTransaction().commit();

        return competitor;
    }

    @Override
    public List<Competitor> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        ArrayList<Competitor> competitors = (ArrayList<Competitor>) session.createQuery("from Competitor").list();

        session.getTransaction().commit();
        return competitors;
    }

    @Override
    public void update(Competitor entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Competitor toUpdate = session.get(Competitor.class, entity.getId());
        toUpdate.setName(entity.getName());
        toUpdate.setDescription(entity.getDescription());

        session.update(toUpdate);
        session.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Competitor toDelete = session.get(Competitor.class, id);
        session.delete(toDelete);
        session.getTransaction().commit();
    }

    @Override
    public List<Competitor> getCompetitorsByGame(int gameId) {
        List<Competitor> matches;

        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        matches = (ArrayList<Competitor>) session.createQuery("from Competitor competitor where competitor.game.id = :gameId").setParameter("gameId", gameId).list();
        session.getTransaction().commit();

        return matches;
    }
}
