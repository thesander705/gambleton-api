package com.gambleton.factory;

import com.gambleton.dataAccessLayer.UserHibernateContext;
import com.gambleton.logic.UserDefaultLogic;
import com.gambleton.logic.abstraction.UserLogic;
import com.gambleton.repository.UserDefaultRepository;

public class Factory {
    public static UserLogic getUserLogic() {
        return new UserDefaultLogic(new UserDefaultRepository(new UserHibernateContext()));
    }
}
