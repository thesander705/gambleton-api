package com.gambleton.dataAccessLayer;

import com.gambleton.dataAccessLayer.abstraction.CompetitorContext;
import com.gambleton.models.Competitor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CompetitorHibernateContext implements CompetitorContext {

    private final SessionFactory sessionFactory;

    public CompetitorHibernateContext() {
        this("hibernate.cfg.xml");
    }

    CompetitorHibernateContext(String filePath){
        try {
            this.sessionFactory = new Configuration().configure(filePath).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    CompetitorHibernateContext(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Competitor entity) {

    }

    @Override
    public Competitor get(int id) {
        return null;
    }

    @Override
    public List<Competitor> getAll() {
        return null;
    }

    @Override
    public void update(Competitor entity) {

    }

    @Override
    public void delete(int id) {

    }
}
