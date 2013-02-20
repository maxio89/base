package com.project.setting;

import com.project.framework.business.Unmanaged;
import pl.com.it_crowd.seam.framework.EntityHome;
import pl.com.it_crowd.utils.config.Setting;
import pl.com.it_crowd.utils.config.SettingDAO;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class SettingHome extends EntityHome<Setting> implements SettingDAO {
// ------------------------------ FIELDS ------------------------------

    @Unmanaged
    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Instance<EntityManager> entityManagerInstance;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface SettingDAO ---------------------

    @Override
    public Setting load(String id)
    {
        clearInstance();
        setId(id);
        return getInstance();
    }

    @Override
    public Setting save(Setting setting)
    {
        clearInstance();
        setInstance(setting);
        if (!update()) {
            throw new IllegalStateException(String.format("Cannot save setting %s:%s", setting.getId(), setting.getValue()));
        }
        return getInstance();
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public EntityManager getEntityManager()
    {
        return entityManagerInstance.get();
    }
}
