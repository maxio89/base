package pl.itcrowd.base.framework.view;

import org.hibernate.exception.ConstraintViolationException;
import org.jboss.seam.international.status.Messages;
import pl.itcrowd.base.framework.business.EntityHome;
import pl.itcrowd.base.web.BundleKeys;

import javax.annotation.Nonnull;
import javax.persistence.PersistenceException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ListViewHelper {

    public static final String OUTCOME_FAILURE = "OUTCOME_FAILURE";

    public static final String OUTCOME_NO_ELEMENT_SELECTED = "noElementSelected";

    public static final String OUTCOME_SUCCESS = "OUTCOME_SUCCESS";

    private ListViewHelper()
    {
    }

    @Nonnull
    public static <T> String removeSelectedElements(@Nonnull Map<T, Boolean> selection, @Nonnull EntityHome<T> dao, @Nonnull Messages messages)
    {
        Set<T> elementsToRemove = new HashSet<T>();
        for (Map.Entry<T, Boolean> entry : selection.entrySet()) {
            if (entry.getValue()) {
                elementsToRemove.add(entry.getKey());
            }
        }
        if (elementsToRemove.isEmpty()) {
            messages.warn(BundleKeys.NO_ELEMENT_SELECTED_WARNING);
            return OUTCOME_NO_ELEMENT_SELECTED;
        }
        /**
         * I don't care about the returned value so it's ignored.
         */
        try {
            dao.remove(elementsToRemove);
            selection.clear();
            messages.info(BundleKeys.SELECTED_ELEMENTES_REMOVED_SUCCESSFULLY);
            return OUTCOME_SUCCESS;
        } catch (PersistenceException e) {
            if (e.getCause() != null && e.getCause() instanceof ConstraintViolationException) {
                final ConstraintViolationException violationException = (ConstraintViolationException) e.getCause();
                if ("23503".equals(violationException.getSQLState())) {
                    messages.error(BundleKeys.DELETE_FOREIGN_KEY_VIOLATION);
                } else {
                    messages.error(violationException.getMessage());
                }
            }
            return OUTCOME_FAILURE;
        }
    }
}
