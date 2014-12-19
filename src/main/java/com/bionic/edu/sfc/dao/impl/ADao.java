package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IDao;
import com.bionic.edu.sfc.entity.Hideable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Ivan
 * 2014.09
 */
@Transactional(Transactional.TxType.MANDATORY)
public abstract class ADao<T extends Hideable> implements IDao<T>{

    private static final Log LOG = LogFactory.getLog(ADao.class);

    protected final Class<T> daoClass;

    @Autowired
    private SessionFactory sessionFactory;

    protected ADao(Class<T> daoClass) {
        this.daoClass = daoClass;
    }

    protected final Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void create(T object) {
        getSession().save(object);
    }

    @Override
    public void update(T object) {
        getSession().update(object);
    }

    @Override
    public void delete(T object) {
        object.setVisible(false);
        changeFieldValues(object, "name");
        getSession().update(object);
    }

    private void changeFieldValues(T object, String propertyName) {
        try {
            Field field = object.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            String value = "[DELETED]" + field.get(object) + "[" + UUID.randomUUID().toString() + "]";
            field.set(object, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException |IllegalAccessException  e) {
//            LOG.error("There is no name", e);
        }
    }

    @Override
    public void deleteWithUniqueFields(T object, String... uniquePropertyNames) {
        if (uniquePropertyNames != null && uniquePropertyNames.length > 0) {
            Stream.of(uniquePropertyNames).forEach(u -> changeFieldValues(object, u));
        }
        object.setVisible(false);
        getSession().update(object);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(long id) {
        return (T) getSession().get(daoClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll(String orderValueName) {
        return getSession()
                .createQuery("FROM " + daoClass.getCanonicalName() + " WHERE visible=true ORDER BY :orderName ")
                .setParameter("orderName", orderValueName)
                .list();
    }
}
