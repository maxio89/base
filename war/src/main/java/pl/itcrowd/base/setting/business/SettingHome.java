package pl.itcrowd.base.setting.business;

import pl.itcrowd.base.framework.business.Unmanaged;
import pl.itcrowd.seam3.persistence.EntityHome;
import pl.itcrowd.utils.config.Setting;
import pl.itcrowd.utils.config.SettingDAO;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * This class implements SettingDAO, which is needed by ProjectConfig.
 */
@SuppressWarnings("UnusedDeclaration")
public class SettingHome extends EntityHome<Setting> implements SettingDAO {
// ------------------------------ FIELDS ------------------------------

    @Unmanaged
    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Instance<EntityManager> entityManagerInstance;

// ------------------------ INTERFACE METHODS ------------------------

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
