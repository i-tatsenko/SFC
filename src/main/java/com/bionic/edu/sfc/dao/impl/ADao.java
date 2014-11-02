package com.bionic.edu.sfc.dao.impl;

import com.bionic.edu.sfc.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Ivan
 * 2014.09
 */
@Transactional(Transactional.TxType.MANDATORY)
public abstract class ADao<T> implements IDao<T>{

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
        getSession().delete(object);
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
                .createQuery("FROM " + daoClass.getCanonicalName() + " ORDER BY :orderName")
                .setParameter("orderName", orderValueName)
                .list();
    }
}
