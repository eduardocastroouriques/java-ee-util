package br.com.auttar.common.pattern.service.database;

import br.com.auttar.common.constants.AppConstants;
import br.com.auttar.common.errorhandling.AppException;
import br.com.auttar.common.pattern.dao.GenericDAO;
import br.com.auttar.common.pattern.data.GenericData;

import javax.ws.rs.core.Response;

public abstract class GenericDatabaseService<T extends GenericData> { //} implements IGenericJPAService<T>{

    public abstract GenericDAO getStorage();

    public T post(T entity) throws Exception {

        if(entity == null)
            throw new AppException(Response.Status.BAD_REQUEST, AppConstants.RESPONSE_CODE_GENERIC_ERROR, AppConstants.RESPONSE_MESSAGE_ERROR_GENERIC);

        getStorage().post(entity);

        entity.setId(null);

        return entity;
    }

    public T get(String id) throws Exception {

        if(id == null)
            throw new AppException(Response.Status.BAD_REQUEST, AppConstants.RESPONSE_CODE_GENERIC_ERROR, AppConstants.RESPONSE_MESSAGE_MISSING_ID);

        T data = (T) getStorage().find(id);

        if(data == null)
            throw new AppException(Response.Status.NOT_FOUND, AppConstants.RESPONSE_CODE_NOT_FOUND, AppConstants.RESPONSE_MESSAGE_NOT_FOOUND);

        data.setId(null);

        return data;
    }
}
