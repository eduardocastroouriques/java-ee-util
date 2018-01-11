package br.com.auttar.common.pattern.service.database;

public interface IGenericJPAService<T> {

    T post(T entity) throws Exception;
    void update(T entity) throws Exception;
    T get(String id) throws Exception;
    void delete(String id) throws Exception;

}
