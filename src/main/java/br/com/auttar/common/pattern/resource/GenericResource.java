package br.com.auttar.common.pattern.resource;

import br.com.auttar.common.builder.ResponseBuilder;
import br.com.auttar.common.errorhandling.AppException;
import br.com.auttar.common.pattern.data.GenericData;
import br.com.auttar.common.pattern.service.GenericService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public abstract class GenericResource<T extends GenericData> implements IGenericResource<T>{

	public abstract GenericService<T> getService();

	@POST
	@Override
	public Response post(T entity) {

		T data = null;

		try{
			data = getService().post(entity);
        }catch (AppException e){
            return new ResponseBuilder<T>().buildFromAppException(e);
        }catch (Exception e){
            return new ResponseBuilder<T>().buildFromException(e);
        }

		return new ResponseBuilder<T>().buildSuccessful(data);
	}

	@PUT
	@Override
	public Response update(T entity) {

		try{
			getService().update(entity);
        }catch (AppException e){
            return new ResponseBuilder<T>().buildFromAppException(e);
        }catch (Exception e){
            return new ResponseBuilder<T>().buildFromException(e);
        }

		return new ResponseBuilder<T>().buildSuccessful(null);
	}

	@GET
	@Path("/{id}")
	@Override
	public Response get(@PathParam("id") String id) {

		T data = null;

		try {
			data = getService().get(id);
		}catch (AppException e){
			return new ResponseBuilder<T>().buildFromAppException(e);
		}catch (Exception e){
			return new ResponseBuilder<T>().buildFromException(e);
		}

		return new ResponseBuilder<T>().buildSuccessful(data);
	}

	@DELETE
	@Override
	public Response delete(@QueryParam("id") String id) {

		try{
			getService().delete(id);
		}catch (AppException e){
			return new ResponseBuilder<T>().buildFromAppException(e);
		}catch (Exception e){
			return new ResponseBuilder<T>().buildFromException(e);
		}

		return new ResponseBuilder<T>().buildSuccessful(null);
	}
}
