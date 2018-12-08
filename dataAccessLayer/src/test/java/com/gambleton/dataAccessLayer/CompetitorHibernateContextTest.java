package com.gambleton.dataAccessLayer;

import org.junit.Test;

public class CompetitorHibernateContextTest {

    private CompetitorHibernateContext competitorHibernateContext;

    @Test(expected = ExceptionInInitializerError.class)
    public void constructorThrowsExceptionWhenSomethingIsWrong(){
        this.competitorHibernateContext = new CompetitorHibernateContext("asd.xml");
    }
}
