package com.gambleton.dataAccessLayer;

import com.gambleton.models.Role;
import com.gambleton.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserHibernateContextIT {
    private SessionFactory sessionFactory;
    UserHibernateContext userHibernateContext;

    @Before
    public void setupEachTest() {
        try {
            sessionFactory = new Configuration().configure("hibernate-test.cfg.xml").buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        userHibernateContext = new UserHibernateContext(sessionFactory);
    }

    @Test
    public void getAllGetsAllUsers() {
        User user = new User();
        user.setAuthToken("1234567890");
        user.setRole(Role.Gambler);
        user.setUsername("test");
        user.setPassword("Test123!");
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(user);
        sessionFactory.getCurrentSession().getTransaction().commit();

        List<User> users = userHibernateContext.getAll();

        for (User userFromCollection : users) {
            if (userFromCollection.getAuthToken().equals("1234567890")){
                Assert.assertTrue(true);
                return;
            }
        }

        Assert.fail();
    }

}
