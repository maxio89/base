package com.project;

import com.project.framework.business.Unmanaged;
import org.jboss.solder.core.ExtensionManaged;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class EntityManagerProducer {
// ------------------------------ FIELDS ------------------------------

    private static final String PERSISTENCE_UNIT_NAME = "project-unit";

    @SuppressWarnings("CdiUnproxyableBeanTypesInspection")
    @ExtensionManaged
    @ConversationScoped
    @Produces
    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

// -------------------------- OTHER METHODS --------------------------

    @Unmanaged
    @Produces
    public EntityManager getStandaloneEntityManager()
    {
        return emf.createEntityManager();
    }
}
