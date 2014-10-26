package com.bionic.edu.sfc.service.dao;

import com.bionic.edu.sfc.dao.IDao;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Ivan
 * 2014.10
 */
@Transactional(Transactional.TxType.REQUIRED)
public interface IService<T> {

    public IDao<T> getDao();

    default public void create(T object) {
        getDao().create(object);
    }

    default public void update(T object) {
        getDao().update(object);
    }

    default public void delete(T object) {
        getDao().delete(object);
    }

    default public T findById(long id) {
        return getDao().findById(id);
    }

    default public List<T> getAll() {
        return getDao().getAll();
    }
}
