package com.gambleton.dataAccessLayer;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;

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

    public void createCreatesAMatch(){

    }
}
