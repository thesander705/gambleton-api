package com.gambleton.dataAccessLayer;

import org.junit.Test;

public class MatchHibernateContextTest {

    private MatchHibernateContext gameHibernateContext;

    @Test(expected = ExceptionInInitializerError.class)
    public void constructorThrowsExceptionWhenSomethingIsWrong(){
        this.gameHibernateContext = new MatchHibernateContext("asd.xml");
    }
}
