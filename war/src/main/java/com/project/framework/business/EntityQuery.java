package com.project.framework.business;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import pl.com.it_crowd.seam.framework.DynParamEntityQuery;

import java.util.List;

public class EntityQuery<E> extends DynParamEntityQuery<E> {

    /**
     * Unproxies all results.
     *
     * @param results results to unproxy
     *
     * @return unproxied results
     */
    @Override
    protected List<E> truncResultList(List<E> results)
    {
        final List<E> list = super.truncResultList(results);
        for (int i = 0; i < list.size(); i++) {
            list.add(unproxy(list.remove(0)));
        }
        return list;
    }

    /**
     * Substitutes proxy with entity.
     *
     * @param entity entity or proxy
     *
     * @return unproxied entity
     */
    @SuppressWarnings({"unchecked"})
    private E unproxy(E entity)
    {
        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy) {
            return (E) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }
        return entity;
    }
}
