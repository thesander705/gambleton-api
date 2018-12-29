package com.gambleton.dataAccessLayer;

import com.gambleton.models.*;
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
    private UserHibernateContext userHibernateContext;

    private User user;

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

    @Before
    public void setupModels() {
        Game game = new Game();
        game.setName("Game");
        game.setDescription("This is a game");

        Competitor competitor1 = new Competitor();
        competitor1.setName("Competitor 1");
        competitor1.setDescription("This is competitor 1");
        competitor1.setGame(game);

        Competitor competitor2 = new Competitor();
        competitor2.setName("Competitor 1");
        competitor2.setDescription("This is competitor 1");
        competitor2.setGame(game);

        BetOption betOption1 = new BetOption();
        betOption1.setPayoutRate(2);
        betOption1.setCompetitor(competitor1);

        BetOption betOption2 = new BetOption();
        betOption2.setPayoutRate(1);
        betOption2.setCompetitor(competitor2);

        Bet bet1 = new Bet();
        bet1.setMoneyPlaced(12);
        bet1.setBetOption(betOption1);

        Bet bet2 = new Bet();
        bet2.setMoneyPlaced(11);
        bet2.setBetOption(betOption2);

        List<Bet> bets = new ArrayList<Bet>();
        bets.add(bet1);
        bets.add(bet2);

        this.user = new User();
        this.user.setAuthToken("1234567890");
        this.user.setRole(Role.Gambler);
        this.user.setUsername("test");
        this.user.setPassword("Test123!");
        this.user.setMoney(123.22);
        this.user.setBets(bets);

        this.saveModels();
    }

    private void saveModels(){
        User user = this.user;
        ArrayList<Object> done = new ArrayList<Object>();
        sessionFactory.getCurrentSession().beginTransaction();
        for (Bet bet : user.getBets()) {
            BetOption betOption = bet.getBetOption();

            if (!done.contains(betOption.getCompetitor().getGame())) {
                sessionFactory.getCurrentSession().save(betOption.getCompetitor().getGame());
                done.add(betOption.getCompetitor().getGame());
            }

            if (!done.contains(betOption.getCompetitor())) {
                sessionFactory.getCurrentSession().save(betOption.getCompetitor());
                done.add(betOption.getCompetitor());
            }

            if (!done.contains(betOption)) {
                sessionFactory.getCurrentSession().save(betOption);
                done.add(betOption);
            }

            if (!done.contains(bet)) {
                sessionFactory.getCurrentSession().save(bet);
                done.add(bet);
            }
        }

        sessionFactory.getCurrentSession().save(user);

        sessionFactory.getCurrentSession().getTransaction().commit();
    }

    @After
    public void afterEachTest() {
        this.sessionFactory.close();
    }

    @Test
    public void constructorThrowsNotAnExceptionWhenEverythingIsOkay() {
        this.userHibernateContext = new UserHibernateContext("hibernate-test.cfg.xml");
    }

    @Test
    public void parameterlessConstructorWorks() {
        try {
            userHibernateContext = new UserHibernateContext();
        } catch (Exception e) {
            Assert.fail();
            return;
        }

        Assert.assertTrue(true);
    }

    @Test
    public void getAllGetsAllUsers() {
        List<User> users = userHibernateContext.getAll();

        for (User userFromCollection : users) {
            if (userFromCollection.getAuthToken().equals("1234567890")) {
                Assert.assertTrue(true);
                return;
            }
        }

        Assert.fail();
    }

    @Test
    public void getByUserNameGetsAUserByUsername() {
        String username = "test";

        User user = this.user;
        user.setUsername(username);
        user.setBets(null);

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(user);
        sessionFactory.getCurrentSession().getTransaction().commit();

        User userToTest = userHibernateContext.getByUsername(username);

        if (userToTest.getUsername().equals(username)) {
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void getByUsernameReturnsNullWhenUserNotFound() {
        User userToTest = userHibernateContext.getByUsername("s");
        Assert.assertNull(userToTest);
    }

    @Test
    public void getByAuthTokenGetsAUserByAuthtoken() {
        String authtoken = "1234567890";

        User user = this.user;
        user.setAuthToken(authtoken);
        user.setBets(null);

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(user);
        sessionFactory.getCurrentSession().getTransaction().commit();

        User userToTest = userHibernateContext.getByAuthToken(authtoken);

        if (userToTest.getAuthToken().equals(authtoken)) {
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void getByAuthTokenReturnsNullWhenUserNotFound() {
        User userToTest = userHibernateContext.getByAuthToken("sadasdasdsa");
        Assert.assertNull(userToTest);
    }

    @Test
    public void createCreatesAUser() {
        User user = this.user;
        user.setBets(null);

        userHibernateContext.create(user);

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        ArrayList<User> users = (ArrayList<User>) session.createQuery("from User").list();

        session.getTransaction().commit();

        if (users != null && !users.isEmpty()) {
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void getGetsAUserById() {
        sessionFactory.getCurrentSession().beginTransaction();

        ArrayList<User> users = (ArrayList<User>) sessionFactory.getCurrentSession().createQuery("from User").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (users == null || users.isEmpty()) {
            Assert.fail();
            return;
        }

        int idToGet = users.get(0).getId();
        User userGotten = userHibernateContext.get(idToGet);

        if (userGotten.getId() == idToGet) {
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void updatedUpdatesAUser() {
        User user = this.user;

        sessionFactory.getCurrentSession().beginTransaction();

        ArrayList<User> users = (ArrayList<User>) sessionFactory.getCurrentSession().createQuery("from User").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (users == null || users.isEmpty()) {
            Assert.fail();
            return;
        }

        User userToGet = users.get(0);
        int userId = userToGet.getId();

        user.setUsername("kees");
        user.setPassword("Kees123!");
        user.setRole(Role.Administrator);
        user.setMoney(12.99);

        this.userHibernateContext.update(user);

        sessionFactory.getCurrentSession().beginTransaction();
        users = (ArrayList<User>) sessionFactory.getCurrentSession().createQuery("from User").list();
        sessionFactory.getCurrentSession().getTransaction().commit();

        for (User userFromCollection : users) {
            if (userFromCollection.getId() != userId) {
                continue;
            }

            if (!userFromCollection.getUsername().equals("kees")) {
                Assert.fail("Name fails");
                return;
            }

            if (!userFromCollection.getPassword().equals("Kees123!")) {
                Assert.fail("password fails");
                return;
            }

            if (userFromCollection.getRole() != Role.Administrator) {
                Assert.fail("role fails");
                return;
            }
            if (userFromCollection.getMoney() != 12.99) {
                Assert.fail("Money fails");
                return;
            }

            Assert.assertTrue(true);
            return;
        }

        Assert.fail("Other fails");
    }

    @Test
    public void deleteDeletesAUser() {
        sessionFactory.getCurrentSession().beginTransaction();

        ArrayList<User> users = (ArrayList<User>) sessionFactory.getCurrentSession().createQuery("from User").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (users == null || users.isEmpty()) {
            Assert.fail();
            return;
        }

        User userToGet = users.get(0);
        int userId = userToGet.getId();

        this.userHibernateContext.delete(userId);

        sessionFactory.getCurrentSession().beginTransaction();

        users = (ArrayList<User>) sessionFactory.getCurrentSession().createQuery("from User").list();

        sessionFactory.getCurrentSession().getTransaction().commit();

        if (users == null || users.isEmpty()) {
            Assert.assertTrue(true);
            return;
        }

        Assert.fail();
    }

    @Test
    public void updateAllowsAddingBets(){
        sessionFactory.getCurrentSession().beginTransaction();
        ArrayList<User> users = (ArrayList<User>) sessionFactory.getCurrentSession().createQuery("from User").list();
        sessionFactory.getCurrentSession().getTransaction().commit();

        if (users == null || users.isEmpty()) {
            Assert.fail();
            return;
        }

        User user = users.get(0);

        BetOption betOption = new BetOption();
        betOption.setPayoutRate(1);
        betOption.setCompetitor(this.user.getBets().get(0).getBetOption().getCompetitor());

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(betOption);
        sessionFactory.getCurrentSession().getTransaction().commit();

        Bet bet = new Bet();
        bet.setMoneyPlaced(88);
        bet.setBetOption(betOption);

        user.getBets().add(bet);
        this.userHibernateContext.update(user);

        sessionFactory.getCurrentSession().beginTransaction();
        users = (ArrayList<User>) sessionFactory.getCurrentSession().createQuery("from User").list();
        sessionFactory.getCurrentSession().getTransaction().commit();

        if (users == null || users.isEmpty()) {
            Assert.fail();
            return;
        }

        user = users.get(0);

        Assert.assertEquals(this.user.getBets().size() + 1, user.getBets().size());
    }
}
