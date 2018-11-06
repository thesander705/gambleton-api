package com.gambleton.factory;

import com.gambleton.dataAccessLayer.GameHibernateContext;
import com.gambleton.dataAccessLayer.UserHibernateContext;
import com.gambleton.logic.GameDefaultLogic;
import com.gambleton.logic.UserDefaultLogic;
import com.gambleton.logic.abstraction.GameLogic;
import com.gambleton.logic.abstraction.UserLogic;
import com.gambleton.repository.GameDefaultRepository;
import com.gambleton.repository.UserDefaultRepository;

public class Factory {
    public static UserLogic getUserLogic() {
        return new UserDefaultLogic(new UserDefaultRepository(new UserHibernateContext()));
    }

    public static GameLogic getGameLogic() {
        return new GameDefaultLogic(new GameDefaultRepository(new GameHibernateContext()));
    }
}
