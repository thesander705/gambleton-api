package com.gambleton.dataAccessLayer;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
}
