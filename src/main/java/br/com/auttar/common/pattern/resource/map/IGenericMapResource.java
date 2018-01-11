package br.com.auttar.common.pattern.resource.map;

import javax.ws.rs.core.Response;

public interface IGenericMapResource<T> {

    Response post(T entity);
    Response update(String id, T entity);
    Response get(String id);
    Response delete(String id);

}
