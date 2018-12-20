package com.gambleton.dataAccessLayer;

import com.gambleton.models.Competitor;
import com.gambleton.models.Game;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CompetitorHibernateContextIT {
    private SessionFactory sessionFactory;
    CompetitorHibernateContext competitorHibernateContext;

    @Before
    public void setupEachTest() {
        try {
            this.sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        competitorHibernateContext = new CompetitorHibernateContext(this.sessionFactory);
    }

    @After
    public void afterEachTest() {
        this.sessionFactory.close();
    }

    @Test
    public void parameterlessConstructorWorks() {
        try {
            competitorHibernateContext = new CompetitorHibernateContext();
        } catch (Exception e) {
            Assert.fail();
            return;
        }

        Assert.assertTrue(true);
    }

    @Test
    public void constructorThrowsNotAnExceptionWhenEverythingIsOkay() {
        this.competitorHibernateContext = new CompetitorHibernateContext("hibernate-test.cfg.xml");
    }

    @Test
    public void createCreatesACompetitor() {
        Game game = new Game();
        game.setName("Test game");
        game.setDescription("This is a test game");
        sessionFactory.getCurrentSession().beginTransaction();
        this.sessionFactory.getCurrentSession().save(game);
        sessionFactory.getCurrentSession().getTransaction().commit();

        Competitor competitor = new Competitor();
        competitor.setName("Test competitor");
        competitor.setDescription("This is a test competitor");
        competitor.setGame(game);

        competitorHibernateContext.create(competitor);

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        ArrayList<Competitor> competitors = (ArrayList<Competitor>) session.createQuery("from Competitor").list();

        session.getTransaction().commit();

        if (competitors != null && !competitors.isEmpty()) {
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void getGetsACompetitorById() {
        Game game = new Game();
        game.setName("Test game");
        game.setDescription("This is a test game");
        sessionFactory.getCurrentSession().beginTransaction();
        this.sessionFactory.getCurrentSession().save(game);
        sessionFactory.getCurrentSession().getTransaction().commit();

        Competitor competitor = new Competitor();
        competitor.setName("Test competitor");
        competitor.setDescription("This is a test competitor");
        competitor.setGame(game);

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(competitor);
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.getCurrentSession().beginTransaction();

        ArrayList<Competitor> competitors = (ArrayList<Competitor>) this.sessionFactory.getCurrentSession().createQuery("from Competitor").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (competitors == null || competitors.isEmpty()) {
            Assert.fail();
            return;
        }

        int idToGet = competitors.get(0).getId();
        Competitor competitorGotten = competitorHibernateContext.get(idToGet);

        if (competitorGotten != null && competitorGotten.getId() == idToGet) {
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void getAllGetsAllCompetitors() {
        Game game = new Game();
        game.setName("Test game");
        game.setDescription("This is a test game");
        sessionFactory.getCurrentSession().beginTransaction();
        this.sessionFactory.getCurrentSession().save(game);
        sessionFactory.getCurrentSession().getTransaction().commit();

        Competitor competitor = new Competitor();
        competitor.setName("Test competitor");
        competitor.setDescription("This is a test competitor");
        competitor.setGame(game);

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(competitor);
        sessionFactory.getCurrentSession().getTransaction().commit();

        List<Competitor> competitors = competitorHibernateContext.getAll();
        if (competitors == null) {
            Assert.fail();
            return;
        }

        for (Competitor userFromCollection : competitors) {
            if (userFromCollection.getName().equals("Test competitor")) {
                Assert.assertTrue(true);
                return;
            }
        }

        Assert.fail();
    }

    @Test
    public void updateUpdatesACompetitor() {
        Game game = new Game();
        game.setName("Test game");
        game.setDescription("This is a test game");
        sessionFactory.getCurrentSession().beginTransaction();
        this.sessionFactory.getCurrentSession().save(game);
        sessionFactory.getCurrentSession().getTransaction().commit();

        Competitor competitor = new Competitor();
        competitor.setName("Test competitor");
        competitor.setDescription("This is a test competitor");
        competitor.setGame(game);

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(competitor);
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.getCurrentSession().beginTransaction();

        ArrayList<Competitor> competitors = (ArrayList<Competitor>) sessionFactory.getCurrentSession().createQuery("from Competitor ").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (competitors == null || competitors.isEmpty()) {
            Assert.fail();
            return;
        }

        Competitor competitorToGet = competitors.get(0);
        int competitorId = competitorToGet.getId();

        competitor.setName("New name");
        competitor.setDescription("New description");

        this.competitorHibernateContext.update(competitor);

        sessionFactory.getCurrentSession().beginTransaction();
        competitors = (ArrayList<Competitor>) sessionFactory.getCurrentSession().createQuery("from Competitor ").list();
        sessionFactory.getCurrentSession().getTransaction().commit();

        for (Competitor competitorFromCollection : competitors) {
            if (competitorFromCollection.getId() != competitorId) {
                continue;
            }

            if (!competitorFromCollection.getName().equals("New name")) {
                Assert.fail();
                return;
            }

            if (!competitorFromCollection.getDescription().equals("New description")) {
                Assert.fail();
                return;
            }

            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void deleteDeletesCompetitor() {
        Game game = new Game();
        game.setName("Test game");
        game.setDescription("This is a test game");
        sessionFactory.getCurrentSession().beginTransaction();
        this.sessionFactory.getCurrentSession().save(game);
        sessionFactory.getCurrentSession().getTransaction().commit();

        Competitor competitor = new Competitor();
        competitor.setName("Test competitor");
        competitor.setDescription("This is a test competitor");
        competitor.setGame(game);

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(competitor);
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.getCurrentSession().beginTransaction();
        ArrayList<Competitor> competitors = (ArrayList<Competitor>) sessionFactory.getCurrentSession().createQuery("from Competitor").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (competitors == null || competitors.isEmpty()) {
            Assert.fail();
            return;
        }

        Competitor competitorToGet = competitors.get(0);
        int competitorId = competitorToGet.getId();

        this.competitorHibernateContext.delete(competitorId);

        sessionFactory.getCurrentSession().beginTransaction();

        competitors = (ArrayList<Competitor>) sessionFactory.getCurrentSession().createQuery("from Competitor ").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (competitors == null || competitors.isEmpty()) {
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void getCompetitorsByGameGetsAllCompetitorsByGame() {
        Game game = new Game();
        game.setName("Test game");
        game.setDescription("This is a test game");
        sessionFactory.getCurrentSession().beginTransaction();
        this.sessionFactory.getCurrentSession().save(game);
        sessionFactory.getCurrentSession().getTransaction().commit();

        Competitor competitor1 = new Competitor();
        competitor1.setName("Test competitor");
        competitor1.setDescription("This is a test competitor");
        competitor1.setGame(game);

        Competitor competitor2 = new Competitor();
        competitor2.setName("Test competitor2");
        competitor2.setDescription("This is a test competitor2");
        competitor2.setGame(game);

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(competitor1);
        sessionFactory.getCurrentSession().save(competitor2);
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.getCurrentSession().beginTransaction();
        List<Game> games = sessionFactory.getCurrentSession().createQuery("from Game ").list();
        sessionFactory.getCurrentSession().getTransaction().commit();

        int gameId = games.get(0).getId();

        List<Competitor> competitors = this.competitorHibernateContext.getCompetitorsByGame(gameId);

        Assert.assertNotNull(competitors);
        Assert.assertEquals(2, competitors.size());
    }
}
