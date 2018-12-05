package com.gambleton.dataAccessLayer;

import com.gambleton.models.BetOption;
import com.gambleton.models.Competitor;
import com.gambleton.models.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MatchHibernateContextIT {
    private SessionFactory sessionFactory;
    private MatchHibernateContext matchHibernateContext;

    @Before
    public void setupEachTest() {
        try {
            this.sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        this.matchHibernateContext = new MatchHibernateContext(this.sessionFactory);
    }

    @After
    public void afterEachTest() {
        this.sessionFactory.close();
    }
    
    @Test
    public void parameterlessConstructorWorks(){
        try{
            matchHibernateContext = new MatchHibernateContext();
        }catch (Exception e){
            Assert.fail();
            return;
        }

        Assert.assertTrue(true);
    }

    @Test
    public void constructorThrowsNotAnExceptionWhenEverythingIsOkay(){
        this.matchHibernateContext = new MatchHibernateContext("hibernate-test.cfg.xml");
    }
    
    @Test
    public void createCreatesAMatch(){
        Match match = new Match();
        match.setTitle("Test match");
        match.setDescription("This is a test match");

        matchHibernateContext.create(match);

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        ArrayList<Match> matches = (ArrayList<Match>) session.createQuery("from Match").list();

        session.getTransaction().commit();

        if (matches != null && !matches.isEmpty()){
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void getGetsAMatchById(){
        Match match = new Match();
        match.setTitle("Test match");
        match.setDescription("This is a test match");

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(match);
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.getCurrentSession().beginTransaction();

        ArrayList<Match> matches = (ArrayList<Match>) this.sessionFactory.getCurrentSession().createQuery("from Match").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (matches == null || matches.isEmpty()){
            Assert.fail();
            return;
        }

        int idToGet = matches.get(0).getId();
        Match matchGotten = matchHibernateContext.get(idToGet);

        if (matchGotten != null && matchGotten.getId() == idToGet){
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }


    @Test
    public void getAllGetsAllMatches() {
        Match match = new Match();
        match.setTitle("Test match");
        match.setDescription("This is a test match");

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(match);
        sessionFactory.getCurrentSession().getTransaction().commit();

        List<Match> matches = matchHibernateContext.getAll();

        if (matches == null){
            Assert.fail();
            return;
        }

        for (Match userFromCollection : matches) {
            if (userFromCollection.getTitle().equals("Test match")){
                Assert.assertTrue(true);
                return;
            }
        }

        Assert.fail();
    }

    @Test
    public void updateUpdatesAMatch(){
        Match match = new Match();
        match.setTitle("Test match");
        match.setDescription("This is a test match");

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(match);
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.getCurrentSession().beginTransaction();

        ArrayList<Match> matches = (ArrayList<Match>) sessionFactory.getCurrentSession().createQuery("from Match ").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (matches == null || matches.isEmpty()){
            Assert.fail();
            return;
        }

        Match matchToGet = matches.get(0);
        int matchId = matchToGet.getId();

        match.setTitle("New name");
        match.setDescription("New description");

        this.matchHibernateContext.update(match);

        sessionFactory.getCurrentSession().beginTransaction();
        matches = (ArrayList<Match>) sessionFactory.getCurrentSession().createQuery("from Match ").list();
        sessionFactory.getCurrentSession().getTransaction().commit();

        for (Match matchFromCollection : matches) {
            if (matchFromCollection.getId() != matchId){
                continue;
            }

            if (!matchFromCollection.getTitle().equals("New name")){
                Assert.fail();
                return;
            }

            if (!matchFromCollection.getDescription().equals("New description")){
                Assert.fail();
                return;
            }

            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void deleteDeletesMatch(){
        Match match = new Match();
        match.setTitle("Test match");
        match.setDescription("This is a test match");

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(match);
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.getCurrentSession().beginTransaction();
        ArrayList<Match> matches = (ArrayList<Match>) sessionFactory.getCurrentSession().createQuery("from Match").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (matches == null || matches.isEmpty()){
            Assert.fail();
            return;
        }

        Match matchToGet = matches.get(0);
        int matchId = matchToGet.getId();

        this.matchHibernateContext.delete(matchId);

        sessionFactory.getCurrentSession().beginTransaction();

        matches = (ArrayList<Match>) sessionFactory.getCurrentSession().createQuery("from Match ").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (matches == null || matches.isEmpty()){
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }
}
