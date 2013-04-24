package pl.itcrowd.base.framework.view;

import pl.itcrowd.base.framework.business.EntityHome;
import pl.itcrowd.base.framework.business.EntitySelected;
import pl.itcrowd.base.web.BundleKeys;
import org.apache.commons.lang.ObjectUtils;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.core.Veto;
import org.jboss.solder.logging.Logger;
import pl.itcrowd.base.framework.business.EntityHome;
import pl.itcrowd.base.framework.business.EntitySelected;
import pl.itcrowd.base.web.BundleKeys;
import pl.itcrowd.seam3.persistence.Identifiable;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.persistence.EntityNotFoundException;
import java.io.Serializable;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
@Veto
public abstract class AbstractDetailsView<E> implements Serializable {
// ------------------------------ FIELDS ------------------------------

    public static final String OUTCOME_FAILURE = "failure";

    @SuppressWarnings("CdiInjectionPointsInspection")
    protected Logger logger;

    protected Messages messages;

    protected ProceedAction proceedAction;

    protected boolean saveChangesConfirmationVisible;

    private boolean modified;

// --------------------------- CONSTRUCTORS ---------------------------

    /**
     * Required for WELD-001435: Normal scoped bean needs no-args constructor to be proxiable
     */
    protected AbstractDetailsView()
    {
    }

    protected AbstractDetailsView(Logger logger, Messages messages)
    {
        this.logger = logger;
        this.messages = messages;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public boolean isModified()
    {
        return modified;
    }

    public boolean isSaveChangesConfirmationVisible()
    {
        return saveChangesConfirmationVisible;
    }

// -------------------------- OTHER METHODS --------------------------

    public String cancel()
    {
        if (isModified()) {
            saveChangesConfirmationVisible = true;
            proceedAction = new ProceedAction() {
                @Override
                public Object invoke()
                {
                    return cancel();
                }
            };
            return OUTCOME_FAILURE;
        }
        return createNew();
    }

    public String createNew()
    {
        if (isModified()) {
            saveChangesConfirmationVisible = true;
            proceedAction = new ProceedAction() {
                @Override
                public Object invoke()
                {
                    return createNew();
                }
            };
            return OUTCOME_FAILURE;
        }
        final EntityHome<E> home = getHome();
        home.clearInstance();
        markNotModified();
        return getOutcomeSuccess();
    }

    public void formChanged()
    {
        markModified();
    }

    public String hideSaveChangesConfirmation()
    {
        saveChangesConfirmationVisible = false;
        return "success";
    }

    public String proceedWithoutSave()
    {
        hideSaveChangesConfirmation();
        markNotModified();
        if (proceedAction != null) {
            final Object outcomeObject = proceedAction.invoke();
            proceedAction = null;
            return outcomeObject == null ? null : outcomeObject.toString();
        }
        logger.warn("proceedWitoutSave called but no proceedAction is set");
        return OUTCOME_FAILURE;
    }

    public String save()
    {
        final EntityHome<E> home = getHome();
        final boolean result = home.isIdDefined() ? home.update() : home.persist();

        if (result) {
            markNotModified();
            final E instance = home.getInstance();
            if (instance instanceof Identifiable) {
                home.setId(((Identifiable) instance).getId());
            }
            messages.info(BundleKeys.DATA_SAVED_SUCCSSFULLY);
            return getOutcomeSuccess();
        } else {
            messages.info(BundleKeys.DATA_CANNOT_BE_SAVED);
            return OUTCOME_FAILURE;
        }
    }

    public String saveAndProceed()
    {
        final String outcome = save();
        if (ObjectUtils.equals(getOutcomeSuccess(), (outcome))) {
            hideSaveChangesConfirmation();
            markNotModified();
            if (proceedAction != null) {
                final Object outcomeObject = proceedAction.invoke();
                proceedAction = null;
                return outcomeObject == null ? null : outcomeObject.toString();
            }
            return outcome;
        } else {
            return outcome;
        }
    }

    /**
     * Makes the entity current instance and marks state as not modified.
     * <p/>
     * Subclasses may override this method and add @Observes(notifyObserver = Reception.IF_EXISTS) @EntitySelected annotations to entity attribute
     * to turn this method into CDI event listener.
     *
     * @param entity selected entity
     */
    public void select(@Observes(notifyObserver = Reception.IF_EXISTS) @EntitySelected final E entity)
    {
        if (isModified()) {
            saveChangesConfirmationVisible = true;
            proceedAction = new ProceedAction() {
                @Override
                public Object invoke()
                {
                    select(entity);
                    return null;
                }
            };
            return;
        }
        final EntityHome<E> home = getHome();
        home.clearInstance();
        if (entity instanceof Identifiable) {
            home.setId(((Identifiable) entity).getId());
            /**
             * In case there is no entity with given id this line will throw exception
             */
            try {
                home.getInstance();
            } catch (EntityNotFoundException e) {
                home.clearInstance();
                throw e;
            }
        }

        markNotModified();
    }

    protected abstract EntityHome<E> getHome();

    protected abstract String getOutcomeSuccess();

    protected void markModified()
    {
        this.modified = true;
    }

    protected void markNotModified()
    {
        this.modified = false;
    }

// -------------------------- INNER CLASSES --------------------------

    protected interface ProceedAction {
// -------------------------- OTHER METHODS --------------------------

        Object invoke();
    }
}
