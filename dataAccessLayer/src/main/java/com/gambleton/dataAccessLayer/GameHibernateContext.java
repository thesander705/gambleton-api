package com.gambleton.dataAccessLayer;

import com.gambleton.dataAccessLayer.abstraction.GameContext;
import com.gambleton.models.Game;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class GameHibernateContext implements GameContext {
    private SessionFactory sessionFactory;

    public GameHibernateContext() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    GameHibernateContext(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Game entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        session.save(entity);

        session.getTransaction().commit();
    }

    @Override
    public Game get(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();


        Game game = session.get(Game.class, id);
        session.getTransaction().commit();

        return game;
    }

    @Override
    public List<Game> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        ArrayList<Game> games = (ArrayList<Game>) session.createQuery("from Game").list();

        session.getTransaction().commit();
        return games;
    }

    @Override
    public void update(Game entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Game toUpdate = session.get(Game.class, entity.getId());
        toUpdate.setName(entity.getName());
        toUpdate.setDescription(entity.getDescription());

        session.update(toUpdate);
        session.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Game toDelete = session.get(Game.class, id);
        session.delete(toDelete);
        session.getTransaction().commit();
    }
}
