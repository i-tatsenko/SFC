package com.bionic.edu.sfc.dao;

import com.bionic.edu.sfc.entity.Hideable;

import java.util.List;

/**
 * Ivan
 * 2014.09
 */
public interface IDao<T extends Hideable> {

    public void create(T object);

    public void update(T object);

    public void delete(T object);

    public void deleteWithUniqueFields(T object, String... uniquePropertyNames);

    public T findById(long id);

    public List<T> getAll(String orderValueName);
}
