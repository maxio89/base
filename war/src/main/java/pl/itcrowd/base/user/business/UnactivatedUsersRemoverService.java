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
 * Service for removing old, unactivated user accounts
 */
@Stateless
public class UnactivatedUsersRemoverService {

    private static final int NUMBER_OF_DAYS = 14; //after 14 days unactivated account will be removed

    @Inject
    @Unmanaged
    private EntityManager entityManager;

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Logger log;

    @SuppressWarnings("UnusedDeclaration")
    @Schedule(hour = "1", minute = "0", second = "0", info = "Unactivated users removal service schedule - once per day at 1:00AM", persistent = true)
    public void executeOnceADay(Timer timer)
    {
        int removedCount = 0;
        log.infov("Start removing unactivated users: {0}", timer.getInfo());
        try {
            removedCount = this.removeUnactivatedAccounts();
        } catch (Exception e) {
            log.error("Cannot remove unactivated accounts.", e);
        }
        log.infov("End of removing unactivated accounts, removed {0} users, next execution: {1} ", removedCount, timer.getNextTimeout().toString());
    }

    /**
     * Method will remove accounts older than 14 days,
     * which aren't active yet.
     *
     * @return removed users count
     */
    @Transactional
    public int removeUnactivatedAccounts()
    {
        Date destinationDate = DateUtils.addDays(new Date(), NUMBER_OF_DAYS);
        String query = "delete User u where u.registrationDate < :date and u.active=false";
        return entityManager.createQuery(query).setParameter("date", destinationDate).executeUpdate();
    }
}
