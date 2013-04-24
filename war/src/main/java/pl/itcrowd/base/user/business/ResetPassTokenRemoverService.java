package pl.itcrowd.base.user.business;

import pl.itcrowd.base.framework.business.Unmanaged;
import org.apache.commons.lang.time.DateUtils;
import org.jboss.seam.transaction.Transactional;
import org.jboss.solder.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Date;

/**
 * Service for removing tokens from DB.
 */
@Stateless
public class ResetPassTokenRemoverService {

    @Inject
    @Unmanaged
    private EntityManager entityManager;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Logger log;

    @SuppressWarnings("UnusedDeclaration")
    @Schedule(hour = "0", minute = "0", second = "0", info = "Token remover service schedule - once per day at 0:00AM", persistent = true)
    public void executeOnceADay(Timer timer)
    {
        int removedCount = 0;
        log.infov("Start removing old password tokens: {0}", timer.getInfo());
        try {
            removedCount = this.removeOldPasswordResetTokens();
        } catch (Exception e) {
            log.error("Cannot remove old password tokens.", e);
        }
        log.infov("End of removing old password tokens, removed {0} tokens, next execution: {1} ", removedCount, timer.getNextTimeout().toString());
    }

    /**
     * Method will remove password tokens older than 24h
     *
     * @return removed tokens count
     */
    @Transactional
    public int removeOldPasswordResetTokens()
    {
        Date dayAgo = DateUtils.addDays(new Date(), -1);
        String query = "delete UserRemindPasswordToken t where t.generationDate < :date";
        return entityManager.createQuery(query).setParameter("date", dayAgo).executeUpdate();
    }
}
