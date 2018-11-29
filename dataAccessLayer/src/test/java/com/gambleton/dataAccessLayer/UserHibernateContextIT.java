package com.gambleton.dataAccessLayer;

import com.gambleton.models.Role;
import com.gambleton.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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

    
    @After
    public void afterEachTest() {
        this.sessionFactory.close();
    }

    @Test
    public void constructorThrowsNotAnExceptionWhenEverythingIsOkay(){
        this.userHibernateContext = new UserHibernateContext("hibernate-test.cfg.xml");
    }

    @Test
    public void parameterlessConstructorWorks(){
        try{
            userHibernateContext = new UserHibernateContext();
        }catch (Exception e){
            Assert.fail();
            return;
        }

        Assert.assertTrue(true);
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

    @Test
    public void getByUserNameGetsAUserByUsername(){
        String username = "test";

        User user = new User();
        user.setAuthToken("1234567890");
        user.setRole(Role.Gambler);
        user.setUsername(username);
        user.setPassword("Test123!");
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(user);
        sessionFactory.getCurrentSession().getTransaction().commit();

        User userToTest = userHibernateContext.getByUsername(username);

        if (userToTest.getUsername().equals(username)){
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void getByUsernameReturnsNullWhenUserNotFound(){
        User userToTest = userHibernateContext.getByUsername("s");
        Assert.assertNull(userToTest);
    }

    @Test
    public void getByAuthTokenGetsAUserByAuthtoken(){
        String authtoken = "1234567890";

        User user = new User();
        user.setAuthToken(authtoken);
        user.setRole(Role.Gambler);
        user.setUsername("test");
        user.setPassword("Test123!");
        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(user);
        sessionFactory.getCurrentSession().getTransaction().commit();

        User userToTest = userHibernateContext.getByAuthToken(authtoken);

        if (userToTest.getAuthToken().equals(authtoken)){
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void getByAuthTokenReturnsNullWhenUserNotFound(){
        User userToTest = userHibernateContext.getByAuthToken("sadasdasdsa");
        Assert.assertNull(userToTest);
    }

    @Test
    public void createCreatesAUser(){
        User user = new User();
        user.setAuthToken("1234567890");
        user.setRole(Role.Gambler);
        user.setUsername("test");
        user.setPassword("Test123!");

        userHibernateContext.create(user);

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        ArrayList<User> users = (ArrayList<User>) session.createQuery("from User").list();

        session.getTransaction().commit();

        if (users != null && !users.isEmpty()){
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void getGetsAUserById(){
        User user = new User();
        user.setAuthToken("1234567890");
        user.setRole(Role.Gambler);
        user.setUsername("test");
        user.setPassword("Test123!");

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(user);
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.getCurrentSession().beginTransaction();

        ArrayList<User> users = (ArrayList<User>) sessionFactory.getCurrentSession().createQuery("from User").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (users == null || users.isEmpty()){
            Assert.fail();
            return;
        }

        int idToGet = users.get(0).getId();
        User userGotten = userHibernateContext.get(idToGet);

        if (userGotten.getId() == idToGet){
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void updatedUpdatesAUser(){
        User user = new User();
        user.setAuthToken("1234567890");
        user.setRole(Role.Gambler);
        user.setUsername("test");
        user.setPassword("Test123!");

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(user);
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.getCurrentSession().beginTransaction();

        ArrayList<User> users = (ArrayList<User>) sessionFactory.getCurrentSession().createQuery("from User").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (users == null || users.isEmpty()){
            Assert.fail();
            return;
        }

        User userToGet = users.get(0);
        int userId = userToGet.getId();

        user.setUsername("kees");
        user.setPassword("Kees123!");
        user.setRole(Role.Administrator);

        this.userHibernateContext.update(user);

        sessionFactory.getCurrentSession().beginTransaction();
        users = (ArrayList<User>) sessionFactory.getCurrentSession().createQuery("from User").list();
        sessionFactory.getCurrentSession().getTransaction().commit();

        for (User userFromCollection : users) {
            if (userFromCollection.getId() != userId){
                continue;
            }

            if (!userFromCollection.getUsername().equals("kees")){
                Assert.fail();
                return;
            }

            if (!userFromCollection.getPassword().equals("Kees123!")){
                Assert.fail();
                return;
            }

            if (userFromCollection.getRole() != Role.Administrator){
                Assert.fail();
                return;
            }

            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void deleteDeletesAUser(){
        User user = new User();
        user.setAuthToken("1234567890");
        user.setRole(Role.Gambler);
        user.setUsername("test");
        user.setPassword("Test123!");

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(user);
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.getCurrentSession().beginTransaction();

        ArrayList<User> users = (ArrayList<User>) sessionFactory.getCurrentSession().createQuery("from User").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (users == null || users.isEmpty()){
            Assert.fail();
            return;
        }

        User userToGet = users.get(0);
        int userId = userToGet.getId();

        this.userHibernateContext.delete(userId);

        sessionFactory.getCurrentSession().beginTransaction();

        users = (ArrayList<User>) sessionFactory.getCurrentSession().createQuery("from User").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (users == null || users.isEmpty()){
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }
}
