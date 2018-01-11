package br.com.auttar.common.pattern.service;

import br.com.auttar.common.constants.AppConstants;
import br.com.auttar.common.errorhandling.AppException;
import br.com.auttar.common.pattern.data.GenericData;
import br.com.auttar.common.pattern.storage.IGenericStorage;

import javax.ws.rs.core.Response;

public abstract class GenericService<T extends GenericData> implements IGenericService<T>{

    public abstract IGenericStorage<T> getStorage();

    @Override
    public T post(T entity) throws Exception {

        if(entity == null)
            throw new AppException(Response.Status.BAD_REQUEST, AppConstants.RESPONSE_CODE_GENERIC_ERROR, AppConstants.RESPONSE_MESSAGE_ERROR_GENERIC);

        T data = getStorage().post(entity);

        data.setId(null);

        return data;
    }

    @Override
    public void update(T entity) throws Exception {

        if(entity == null)
            throw new AppException(Response.Status.BAD_REQUEST, AppConstants.RESPONSE_CODE_GENERIC_ERROR, AppConstants.RESPONSE_MESSAGE_ERROR_GENERIC);

        getStorage().update(entity);
    }

    @Override
    public T get(String id) throws Exception {

        if(id == null)
            throw new AppException(Response.Status.BAD_REQUEST, AppConstants.RESPONSE_CODE_GENERIC_ERROR, AppConstants.RESPONSE_MESSAGE_MISSING_ID);

        if(getStorage() == null || getStorage() == null)
            throw new AppException(Response.Status.BAD_REQUEST, AppConstants.RESPONSE_CODE_GENERIC_ERROR, AppConstants.RESPONSE_MESSAGE_NOT_FOOUND);

        T data = getStorage().get(id);

        if(data == null)
            throw new AppException(Response.Status.NOT_FOUND, AppConstants.RESPONSE_CODE_NOT_FOUND, AppConstants.RESPONSE_MESSAGE_NOT_FOOUND);

        data.setId(null);

        return data;
    }

    @Override
    public void delete(String id) throws Exception {

        if(id == null)
            throw new AppException(Response.Status.BAD_REQUEST, AppConstants.RESPONSE_CODE_GENERIC_ERROR, AppConstants.RESPONSE_MESSAGE_MISSING_ID);

        getStorage().delete(id);
    }
}
