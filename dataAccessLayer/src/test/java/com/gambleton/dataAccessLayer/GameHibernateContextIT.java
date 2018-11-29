package com.gambleton.dataAccessLayer;

import com.gambleton.models.Game;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
    public void constructorThrowsNotAnExceptionWhenEverythingIsOkay(){
        this.gameHibernateContext = new GameHibernateContext("hibernate-test.cfg.xml");
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

    @Test
    public void getGetsAGameById(){
        Game game = new Game();
        game.setName("Test game");
        game.setDescription("This is a test game");

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(game);
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.getCurrentSession().beginTransaction();

        ArrayList<Game> games = (ArrayList<Game>) this.sessionFactory.getCurrentSession().createQuery("from Game").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (games == null || games.isEmpty()){
            Assert.fail();
            return;
        }

        int idToGet = games.get(0).getId();
        Game gameGotten = gameHibernateContext.get(idToGet);

        if (gameGotten.getId() == idToGet){
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void getAllGetsAllGames() {
        Game game = new Game();
        game.setName("Test game");
        game.setDescription("This is a test game");

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(game);
        sessionFactory.getCurrentSession().getTransaction().commit();

        List<Game> games = gameHibernateContext.getAll();

        for (Game userFromCollection : games) {
            if (userFromCollection.getName().equals("Test game")){
                Assert.assertTrue(true);
                return;
            }
        }

        Assert.fail();
    }

    @Test
    public void updateUpdatesAGame(){
        Game game = new Game();
        game.setName("Test game");
        game.setDescription("This is a test game");

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(game);
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.getCurrentSession().beginTransaction();

        ArrayList<Game> games = (ArrayList<Game>) sessionFactory.getCurrentSession().createQuery("from Game ").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (games == null || games.isEmpty()){
            Assert.fail();
            return;
        }

        Game gameToGet = games.get(0);
        int gameId = gameToGet.getId();

        game.setName("New name");
        game.setDescription("New description");

        this.gameHibernateContext.update(game);

        sessionFactory.getCurrentSession().beginTransaction();
        games = (ArrayList<Game>) sessionFactory.getCurrentSession().createQuery("from Game ").list();
        sessionFactory.getCurrentSession().getTransaction().commit();

        for (Game gameFromCollection : games) {
            if (gameFromCollection.getId() != gameId){
                continue;
            }

            if (!gameFromCollection.getName().equals("New name")){
                Assert.fail();
                return;
            }

            if (!gameFromCollection.getDescription().equals("New description")){
                Assert.fail();
                return;
            }

            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void deleteDeletesGame(){
        Game game = new Game();
        game.setName("Test game");
        game.setDescription("This is a test game");

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(game);
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.getCurrentSession().beginTransaction();
        ArrayList<Game> games = (ArrayList<Game>) sessionFactory.getCurrentSession().createQuery("from Game").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (games == null || games.isEmpty()){
            Assert.fail();
            return;
        }

        Game gameToGet = games.get(0);
        int gameId = gameToGet.getId();

        this.gameHibernateContext.delete(gameId);

        sessionFactory.getCurrentSession().beginTransaction();

        games = (ArrayList<Game>) sessionFactory.getCurrentSession().createQuery("from Game ").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (games == null || games.isEmpty()){
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }
}
