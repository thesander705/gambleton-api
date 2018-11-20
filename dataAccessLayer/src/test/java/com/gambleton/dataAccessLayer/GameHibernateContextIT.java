package com.gambleton.dataAccessLayer;

import com.gambleton.models.Game;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GameHibernateContextIT {
    private SessionFactory sessionFactory;
    GameHibernateContext gameHibernateContext;

    @Before
    public void setupEachTest() {
        try {
            this.sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        gameHibernateContext = new GameHibernateContext(this.sessionFactory);
    }

    @Test
    public void parameterlessConstructorWorks(){
        try{
            gameHibernateContext = new GameHibernateContext();
        }catch (Exception e){
            Assert.fail();
            return;
        }

        Assert.assertTrue(true);
    }

    @Test
    public void createCreatesAUser(){
        Game game = new Game();
        game.setName("Test game");
        game.setDescription("This is a test game");

        gameHibernateContext.create(game);

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        ArrayList<Game> games = (ArrayList<Game>) session.createQuery("from Game").list();

        session.getTransaction().commit();

        if (games != null && !games.isEmpty()){
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }
}
