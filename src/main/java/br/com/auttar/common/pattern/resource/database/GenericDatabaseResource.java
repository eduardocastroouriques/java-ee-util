package br.com.auttar.common.pattern.resource.database;

import br.com.auttar.common.builder.ResponseBuilder;
import br.com.auttar.common.errorhandling.AppException;
import br.com.auttar.common.pattern.data.GenericData;
import br.com.auttar.common.pattern.service.database.GenericDatabaseService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public abstract class GenericDatabaseResource<T extends GenericData> { // implements IGenericResource<T>{

	public abstract GenericDatabaseService<T> getService();

	@POST
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

	@GET
	@Path("/{id}")
	public Response get(@PathParam("id") String id) {

		T data = null;

		try{
			data = getService().get(id);
		}catch (AppException e){
			return new ResponseBuilder<T>().buildFromAppException(e);
		}catch (Exception e){
			return new ResponseBuilder<T>().buildFromException(e);
		}

		return new ResponseBuilder<T>().buildSuccessful(data);
	}
}
