package pl.itcrowd.base.framework.view;

import org.jboss.seam.international.status.Messages;
import pl.itcrowd.base.Constants;
import pl.itcrowd.base.framework.business.EntityHome;
import pl.itcrowd.base.framework.business.EntityQuery;
import pl.itcrowd.seam3.persistence.EntityPersisted;
import pl.itcrowd.seam3.persistence.EntityRemoved;
import pl.itcrowd.seam3.persistence.EntityUpdated;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("CdiManagedBeanInconsistencyInspection")
public abstract class AbstractListView<T> {
// ------------------------------ FIELDS ------------------------------

    protected EntityHome<T> entityHome;

    protected EntityQuery<T> entityQuery;

    protected Event<T> entitySelectedEvent;

    protected Map<T, Boolean> entitySelection = new HashMap<T, Boolean>();

    protected Messages messages;

// --------------------------- CONSTRUCTORS ---------------------------

    protected AbstractListView()
    {
    }

    protected AbstractListView(@Nonnull EntityHome<T> entityHome, @Nonnull EntityQuery<T> entityQuery, @Nonnull Event<T> entitySelectedEvent, @Nonnull Messages messages)
    {
        this.entityHome = entityHome;
        this.entityQuery = entityQuery;
        this.entitySelectedEvent = entitySelectedEvent;
        this.messages = messages;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    @Nonnull
    public Map<T, Boolean> getEntitySelection()
    {
        return entitySelection;
    }

// -------------------------- OTHER METHODS --------------------------

    @SuppressWarnings("UnusedDeclaration")
    public void filterSelection()
    {
        final Set<T> composers = new HashSet<T>(getEntityList().getResultList());
        for (Iterator<Map.Entry<T, Boolean>> iterator = entitySelection.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<T, Boolean> entry = iterator.next();
            if (!composers.contains(entry.getKey())) {
                iterator.remove();
            }
        }
    }

    @Nonnull
    public EntityQuery<T> getEntityList()
    {
        return entityQuery;
    }

    @PostConstruct
    public void init()
    {
        entityQuery.setMaxResults(Constants.DEFAULT_MAX_RESULTS);
    }

    @Nonnull
    public String removeSelectedEntities()
    {
        return ListViewHelper.removeSelectedElements(entitySelection, entityHome, messages);
    }

    @Nonnull
    public String select(T entity)
    {
        entitySelectedEvent.fire(entity);
        return "success";
    }

    /**
     * Observers for entity events
     *
     * @param entity
     */
    @SuppressWarnings({"UnusedDeclaration", "JavaDoc"})
    private void onEntityPersisted(@Observes(notifyObserver = Reception.IF_EXISTS) @EntityPersisted T entity)
    {
        entityQuery.refresh();
    }

    @SuppressWarnings("UnusedDeclaration")
    private void onEntityRemoved(@Observes(notifyObserver = Reception.IF_EXISTS) @EntityRemoved T entity)
    {
        entityQuery.refresh();
    }

    @SuppressWarnings("UnusedDeclaration")
    private void onEntityUpdated(@Observes(notifyObserver = Reception.IF_EXISTS) @EntityUpdated T entity)
    {
        entityQuery.refresh();
    }
}
