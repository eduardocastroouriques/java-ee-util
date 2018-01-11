package br.com.auttar.common.pattern.storage.map;

public interface IGenericMapStorage<T> {

    T post(String key, T entity) throws Exception;
    void update(String key, T entity) throws Exception;
    T get(String key) throws Exception;
    void delete(String key) throws Exception;

}
