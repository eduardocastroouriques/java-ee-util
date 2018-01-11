package br.com.auttar.common.builder;

import br.com.auttar.common.constants.AppConstants;
import br.com.auttar.common.errorhandling.AppException;
import br.com.auttar.common.model.ObjectDataModel;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

public class ResponseBuilder<T> {

	public ResponseBuilder() {
	}

	public Response buildSuccessful(T data){

		ObjectDataModel<T> objectDataModel = new ObjectDataModel<>();

		objectDataModel.setCode(AppConstants.RESPONSE_CODE_SUCCESS);
		objectDataModel.setMessage(Response.Status.OK.getReasonPhrase());
		objectDataModel.setStatus(Response.Status.OK.getStatusCode());

		if(data instanceof List<?>){
			objectDataModel.setOffset(1);
			objectDataModel.setLimit(((List<?>) data).size());
		}

		if(data != null)
			objectDataModel.setData(data);

		return Response.status(objectDataModel.getStatus()).entity(objectDataModel).build();
	}

	public Response buildFromAppException(AppException e){

		ObjectDataModel<T> objectDataModel = new ObjectDataModel<>();

		objectDataModel.setCode( e.getCode());
		objectDataModel.setMessage( e.getMessage());
		objectDataModel.setStatus( e.getStatus());

		return Response.status(objectDataModel.getStatus()).entity(objectDataModel).build();
	}

	public Response buildFromException(Exception e){

		ObjectDataModel<T> objectDataModel = new ObjectDataModel<>();

		objectDataModel.setCode( AppConstants.RESPONSE_CODE_GENERIC_ERROR );
		objectDataModel.setMessage( AppConstants.RESPONSE_MESSAGE_ERROR_GENERIC );
		objectDataModel.setStatus( Status.INTERNAL_SERVER_ERROR.getStatusCode() );

		return Response.status(objectDataModel.getStatus()).entity(objectDataModel).build();
	}
	
}
