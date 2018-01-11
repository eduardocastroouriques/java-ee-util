package br.com.auttar.common.pattern.resource;

import javax.ws.rs.core.Response;

public interface IGenericResource<T> {

    Response post(T entity);
    Response update(T entity);
    Response get(String id);
    Response delete(String id);

}
