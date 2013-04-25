package pl.itcrowd.base.security;

import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.logging.Logger;
import pl.itcrowd.base.framework.business.Unmanaged;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Date;

@Stateless
public class RememberMeTokenRemoverService {

    private static final int TOKEN_EXPIRED_HOURS = 1;

    @Inject
    @Unmanaged
    private EntityManager entityManager;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Logger log;

    @SuppressWarnings("UnusedDeclaration")
    @Schedule(hour = "2", minute = "0", second = "0", info = "RememberMe token removal service schedule - once per day at 2:00AM", persistent = true)
    public void executeOnceADay(Timer timer)
    {
        int removedCount = 0;
        log.infov("Start removing RememberMe tokens: {0}", timer.getInfo());
        try {
            removedCount = this.removeExpiredTokens();
        } catch (Exception e) {
            log.error("Cannot remove token.", e);
        }
        log.infov("End of removing RememberMe tokens, removed {0}, next execution: {1} ", removedCount, timer.getNextTimeout().toString());
    }

    @Transactional
    public int removeExpiredTokens()
    {
        Date destinationDate = DateUtils.addHours(new Date(), TOKEN_EXPIRED_HOURS);
        String query = "delete RememberMeToken t where t.created < :date";
        return entityManager.createQuery(query).setParameter("date", destinationDate).executeUpdate();
    }
}
