package com.gambleton.factory;

import com.gambleton.dataAccessLayer.CompetitorHibernateContext;
import com.gambleton.dataAccessLayer.GameHibernateContext;
import com.gambleton.dataAccessLayer.MatchHibernateContext;
import com.gambleton.dataAccessLayer.UserHibernateContext;
import com.gambleton.logic.CompetitorDefaultLogic;
import com.gambleton.logic.GameDefaultLogic;
import com.gambleton.logic.MatchDefaultLogic;
import com.gambleton.logic.UserDefaultLogic;
import com.gambleton.logic.abstraction.CompetitorLogic;
import com.gambleton.logic.abstraction.GameLogic;
import com.gambleton.logic.abstraction.MatchLogic;
import com.gambleton.logic.abstraction.UserLogic;
import com.gambleton.repository.CompetitorDefaultRepository;
import com.gambleton.repository.GameDefaultRepository;
import com.gambleton.repository.MatchDefaultRepository;
import com.gambleton.repository.UserDefaultRepository;

public class Factory {
    public static UserLogic getUserLogic() {
        return new UserDefaultLogic(new UserDefaultRepository(new UserHibernateContext()));
    }

    public static GameLogic getGameLogic() {
        return new GameDefaultLogic(new GameDefaultRepository(new GameHibernateContext()));
    }

    public static MatchLogic getMatchLogic() {
        return new MatchDefaultLogic(new MatchDefaultRepository(new MatchHibernateContext()));
    }

    public static CompetitorLogic getCompetitorLogic() {
        return new CompetitorDefaultLogic(new CompetitorDefaultRepository(new CompetitorHibernateContext()), new GameDefaultRepository(new GameHibernateContext()));
    }
}
