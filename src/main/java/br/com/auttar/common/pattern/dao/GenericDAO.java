package br.com.auttar.common.pattern.dao;

import br.com.auttar.common.constants.AppConstants;
import br.com.auttar.common.errorhandling.AppException;
import br.com.auttar.common.pattern.data.GenericData;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;

public abstract class GenericDAO<T extends GenericData> {

    public abstract EntityManager getEntityManager();
    public abstract Class<T> getClazz();


    public void post(T entity) throws AppException {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException(Response.Status.INTERNAL_SERVER_ERROR, AppConstants.RESPONSE_CODE_GENERIC_ERROR, ex.getMessage());
        }
    }

    public T update(T entity) throws AppException {

        T data = null;

        try {

            getEntityManager().merge(entity);
            getEntityManager().flush();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException(Response.Status.INTERNAL_SERVER_ERROR, AppConstants.RESPONSE_CODE_GENERIC_ERROR, ex.getMessage());
        }

        return data;
    }

    public T find(String id) throws AppException {

        T data = null;

        try {

            getEntityManager().find(getClazz(), id);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppException(Response.Status.INTERNAL_SERVER_ERROR, AppConstants.RESPONSE_CODE_GENERIC_ERROR, ex.getMessage());
        }
        return data;
    }
}
