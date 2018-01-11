package br.com.auttar.common.pattern.storage.map;

import br.com.auttar.common.constants.AppConstants;
import br.com.auttar.common.errorhandling.AppException;

import javax.ejb.Singleton;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class GenericMapStorage<T>  implements IGenericMapStorage<T> {

    private Map<String, T> storage = new HashMap<>();

    @Override
    public T post(String key, T entity){

        if(!storage.containsKey(key))
            storage.put(key, entity);

        return storage.get(key);
    }

    @Override
    public void update(String key, T entity) {
        storage.put(key, entity);
    }

    @Override
    public T get(String key) {
        return storage.get(key);
    }

    @Override
    public void delete(String key) throws Exception{

        if(!storage.containsKey(key))
            throw new AppException(Response.Status.BAD_REQUEST, AppConstants.RESPONSE_CODE_GENERIC_ERROR, AppConstants.RESPONSE_MESSAGE_NOT_FOOUND);
        
        storage.remove(key);
    }
}
