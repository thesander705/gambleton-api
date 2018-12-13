package com.gambleton.dataAccessLayer;

import com.gambleton.models.BetOption;
import com.gambleton.models.Competitor;
import com.gambleton.models.Game;
import com.gambleton.models.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        Game game = new Game();
        game.setDescription("Tennis game");
        game.setName("Tennis");
        sessionFactory.getCurrentSession().beginTransaction();
        this.sessionFactory.getCurrentSession().save(game);
        sessionFactory.getCurrentSession().getTransaction().commit();

        Competitor competitor1 = new Competitor();
        competitor1.setDescription("Test competitor");
        competitor1.setName("Test competitor");
        competitor1.setGame(game);

        Competitor competitor2 = new Competitor();
        competitor2.setDescription("Test competitor2");
        competitor2.setName("Test competitor2");
        competitor2.setGame(game);

        sessionFactory.getCurrentSession().beginTransaction();
        this.sessionFactory.getCurrentSession().save(competitor1);
        this.sessionFactory.getCurrentSession().save(competitor2);
        sessionFactory.getCurrentSession().getTransaction().commit();


        BetOption betOption1 = new BetOption();
        betOption1.setPayoutRate(2);
        betOption1.setCompetitor(competitor1);

        BetOption betOption2 = new BetOption();
        betOption2.setPayoutRate(1);
        betOption2.setCompetitor(competitor2);

        Match match = new Match();
        match.setTitle("name");
        match.setDescription("description");
        List<BetOption> betOptions = new ArrayList<>();
        betOptions.add(betOption1);
        betOptions.add(betOption2);
        match.setBetOptions(betOptions);
        match.setGame(game);

        Calendar calendar = new Calendar.Builder().build();
        calendar.set(Calendar.YEAR , 2018);
        calendar.set(Calendar.MONTH , 12);
        calendar.set(Calendar.DAY_OF_MONTH , 13);
        calendar.set(Calendar.HOUR , 20);
        calendar.set(Calendar.MINUTE , 30);
        Date date = calendar.getTime();
        match.setStartDate(date);
        calendar.set(Calendar.HOUR , 22);
        calendar.set(Calendar.MINUTE , 45);
        date = calendar.getTime();
        match.setEndDate(date);

        try{
            matchHibernateContext.create(match);
        }catch (Exception e){
            Assert.fail();
            return;
        }

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

        Game game = new Game();
        game.setDescription("Tennis game");
        game.setName("Tennis");
        sessionFactory.getCurrentSession().beginTransaction();
        this.sessionFactory.getCurrentSession().save(game);
        sessionFactory.getCurrentSession().getTransaction().commit();

        Competitor competitor1 = new Competitor();
        competitor1.setDescription("Test competitor");
        competitor1.setName("Test competitor");
        competitor1.setGame(game);

        Competitor competitor2 = new Competitor();
        competitor2.setDescription("Test competitor2");
        competitor2.setName("Test competitor2");
        competitor2.setGame(game);

        sessionFactory.getCurrentSession().beginTransaction();
        this.sessionFactory.getCurrentSession().save(competitor1);
        this.sessionFactory.getCurrentSession().save(competitor2);
        sessionFactory.getCurrentSession().getTransaction().commit();

        BetOption betOption1 = new BetOption();
        betOption1.setPayoutRate(2);
        betOption1.setCompetitor(competitor1);

        BetOption betOption2 = new BetOption();
        betOption2.setPayoutRate(1);
        betOption2.setCompetitor(competitor2);

        sessionFactory.getCurrentSession().beginTransaction();
        this.sessionFactory.getCurrentSession().save(betOption1);
        this.sessionFactory.getCurrentSession().save(betOption2);
        sessionFactory.getCurrentSession().getTransaction().commit();

        Match match = new Match();
        match.setTitle("name");
        match.setDescription("description");
        List<BetOption> betOptions = new ArrayList<>();
        betOptions.add(betOption1);
        betOptions.add(betOption2);
        match.setBetOptions(betOptions);
        match.setGame(game);

        Calendar calendar = new Calendar.Builder().build();
        calendar.set(2018, 11, 13, 20, 30);
        Date date = calendar.getTime();
        match.setStartDate(date);
        calendar.set(2018, 11, 13, 22, 30);
        date = calendar.getTime();
        match.setEndDate(date);


        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().save(match);
        sessionFactory.getCurrentSession().getTransaction().commit();

        sessionFactory.getCurrentSession().beginTransaction();

        ArrayList<Match> matches = (ArrayList<Match>) sessionFactory.getCurrentSession().createQuery("from Match ").list();
        Match matchToGet = matches.get(0);
        int matchId = matchToGet.getId();


        sessionFactory.getCurrentSession().getTransaction().commit();

        if (matches == null || matches.isEmpty()){
            Assert.fail();
            return;
        }

        match.setTitle("New name");
        match.setDescription("New description");
        match.getBetOptions().get(0).setPayoutRate(22);

        calendar = new Calendar.Builder().build();
        calendar.set(2019, 10, 12, 22, 30);
        date = calendar.getTime();
        match.setStartDate(date);
        calendar.set(2019, 10, 12, 23, 30);
        date = calendar.getTime();
        match.setEndDate(date);
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

            if (matchFromCollection.getBetOptions().get(0).getPayoutRate() != 22){
                Assert.fail();
                return;
            }
            calendar.setTime(matchFromCollection.getStartDate());
            if (calendar.get(Calendar.YEAR) != 2019 ){
                Assert.fail();
                return;
            }

            calendar.setTime(matchFromCollection.getEndDate());
            if (calendar.get(Calendar.YEAR) != 2019){
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
