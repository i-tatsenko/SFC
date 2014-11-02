package com.bionic.edu.sfc.dao;

import java.util.List;

/**
 * Ivan
 * 2014.09
 */
public interface IDao<T> {

    public void create(T object);

    public void update(T object);

    public void delete(T object);

    public T findById(long id);

    public List<T> getAll(String orderValueName);
}
