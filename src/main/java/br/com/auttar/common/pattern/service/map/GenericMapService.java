package br.com.auttar.common.pattern.service.map;

import br.com.auttar.common.constants.AppConstants;
import br.com.auttar.common.errorhandling.AppException;
import br.com.auttar.common.pattern.data.GenericData;
import br.com.auttar.common.pattern.service.IGenericService;
import br.com.auttar.common.pattern.storage.map.GenericMapStorage;
import br.com.auttar.common.util.StringUtils;

import javax.ws.rs.core.Response;

public abstract class GenericMapService<T extends GenericData> implements IGenericService<T>{

    public abstract GenericMapStorage<T> getStorage();

    @Override
    public T post(T entity) throws Exception {

        if(entity == null)
            throw new AppException(Response.Status.BAD_REQUEST, AppConstants.RESPONSE_CODE_GENERIC_ERROR, AppConstants.RESPONSE_MESSAGE_ERROR_GENERIC);

        T data = getStorage().post(entity.getId(), entity);

        data.setId(null);

        return data;
    }

    @Override
    public void update(T entity) throws Exception {

        if(entity == null)
            throw new AppException(Response.Status.BAD_REQUEST, AppConstants.RESPONSE_CODE_GENERIC_ERROR, AppConstants.RESPONSE_MESSAGE_ERROR_GENERIC);

        getStorage().update(entity.getId(), entity);
    }

    @Override
    public T get(String id) throws Exception {

        if(id == null || StringUtils.isEmptyString(id))
            throw new AppException(Response.Status.BAD_REQUEST, AppConstants.RESPONSE_CODE_GENERIC_ERROR, AppConstants.RESPONSE_MESSAGE_MISSING_ID);

        if(getStorage() == null || getStorage().get(id) == null)
            throw new AppException(Response.Status.BAD_REQUEST, AppConstants.RESPONSE_CODE_GENERIC_ERROR, AppConstants.RESPONSE_MESSAGE_NOT_FOOUND);

        T data = getStorage().get(id);

        data.setId(null);

        return data;
    }

    @Override
    public void delete(String id) throws Exception {

        if(id == null || StringUtils.isEmptyString(id))
            throw new AppException(Response.Status.BAD_REQUEST, AppConstants.RESPONSE_CODE_GENERIC_ERROR, AppConstants.RESPONSE_MESSAGE_MISSING_ID);

        if(getStorage() == null || getStorage().get(id) == null)
            throw new AppException(Response.Status.BAD_REQUEST, AppConstants.RESPONSE_CODE_GENERIC_ERROR, AppConstants.RESPONSE_MESSAGE_NOT_FOOUND);

        getStorage().delete(id);
    }
}
