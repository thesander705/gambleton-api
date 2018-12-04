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
    public void createCreatesAMatch(){
        Match match = new Match();
        match.setTitle("Test match");
        match.setDescription("This is a test match");

        matchHibernateContext.create(match);

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        ArrayList<Match> matchs = (ArrayList<Match>) session.createQuery("from Match").list();

        session.getTransaction().commit();

        if (matchs != null && !matchs.isEmpty()){
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }
}
