package br.com.auttar.common.pattern.storage;

public abstract class GenericStorage<T> implements IGenericStorage<T> {

    public T storage;

    @Override
    public T post(T entity) {
        return storage = entity;
    }

    @Override
    public void update(T entity) {
        storage = entity;
    }

    @Override
    public T get(String id) {
        return storage;
    }

    @Override
    public void delete(String id) {
        storage = null;
    }
}
