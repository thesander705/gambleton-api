package com.gambleton.dataAccessLayer;

import org.junit.Test;

public class GameHibernateContextTest {

    private GameHibernateContext gameHibernateContext;

    @Test(expected = ExceptionInInitializerError.class)
    public void constructorThrowsExceptionWhenSomethingIsWrong(){
        this.gameHibernateContext = new GameHibernateContext("asd.xml");
    }
}
