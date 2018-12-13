package com.gambleton.dataAccessLayer;

import com.gambleton.dataAccessLayer.abstraction.MatchContext;
import com.gambleton.models.BetOption;
import com.gambleton.models.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatchHibernateContext implements MatchContext {
    private final SessionFactory sessionFactory;

    public MatchHibernateContext() {
        this("hibernate.cfg.xml");
    }

    MatchHibernateContext(String filePath){
        try {
            this.sessionFactory = new Configuration().configure(filePath).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    MatchHibernateContext(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Match entity) {
        Session session = sessionFactory.getCurrentSession();
        List<BetOption> betOptions = entity.getBetOptions();

        session.beginTransaction();

        for (BetOption betOption : betOptions) {
            session.save(betOption);
        }

        session.save(entity);
        session.getTransaction().commit();
    }

    @Override
    public Match get(int id) {
        Match match;
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        match = session.get(Match.class, id);

        session.getTransaction().commit();

        return match;
    }

    @Override
    public List<Match> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        ArrayList<Match> matches = (ArrayList<Match>) session.createQuery("from Match").list();

        session.getTransaction().commit();
        return matches;
    }

    @Override
    public void update(Match entity) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Match toUpdate = session.get(Match.class, entity.getId());
        for (final BetOption betOption : toUpdate.getBetOptions()) {
            BetOption betOptionFromEntity = entity.getBetOptions().stream().filter(e -> e.getId() == betOption.getId()).collect(Collectors.toList()).get(0);
            betOption.setPayoutRate(betOptionFromEntity.getPayoutRate());
            betOption.setCompetitor(betOptionFromEntity.getCompetitor());
            session.update(betOption);
        }

        toUpdate.setTitle(entity.getTitle());
        toUpdate.setDescription(entity.getDescription());
        toUpdate.setBetOptions(entity.getBetOptions());
        toUpdate.setStartDate(entity.getStartDate());
        toUpdate.setEndDate(entity.getEndDate());

        session.update(toUpdate);
        session.getTransaction().commit();
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Match toDelete = session.get(Match.class, id);
        session.delete(toDelete);
        session.getTransaction().commit();
    }

    @Override
    public List<Match> getMatchesByGame(int gameId) {
        List<Match> matches;

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        matches = (ArrayList<Match>) session.createQuery("from Match match where match.game.id = :gameId").setParameter("gameId", gameId).list();

        session.getTransaction().commit();

        return matches;
    }
}
