package com.gambleton.dataAccessLayer;

import org.junit.Test;

public class UserHibernateContextTest {

    private UserHibernateContext userHibernateContext;

    @Test(expected = ExceptionInInitializerError.class)
    public void constructorThrowsExceptionWhenSomethingIsWrong(){
        this.userHibernateContext = new UserHibernateContext("asd.xml");
    }
}
