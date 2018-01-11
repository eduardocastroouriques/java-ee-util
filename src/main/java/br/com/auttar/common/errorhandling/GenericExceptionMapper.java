package br.com.auttar.common.errorhandling;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import br.com.auttar.common.constants.AppConstants;
import br.com.auttar.common.constants.LogConstants;
import br.com.auttar.common.model.ObjectDataModel;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	private Logger logger = LogManager.getLogger(LogConstants.ERRORHANDLING_LOGGER_NAME);
	
	public GenericExceptionMapper(){}
	
	public Response toResponse(Throwable ex) {

    	logger.error(ex.getMessage(), ex);
		
		ObjectDataModel<?> objectDataModel = new ObjectDataModel<>();		
		setHttpStatus(ex, objectDataModel);
		objectDataModel.setCode(AppConstants.RESPONSE_CODE_GENERIC_ERROR);
		objectDataModel.setMessage(ex.getMessage());
		StringWriter errorStackTrace = new StringWriter();
		ex.printStackTrace(new PrintWriter(errorStackTrace));
				
		return Response.status(objectDataModel.getStatus())
				.entity(objectDataModel)
				.type(MediaType.APPLICATION_JSON)
				.build();	
	}

	private void setHttpStatus(Throwable ex, ObjectDataModel<?> objectDataModel) {
		if(ex instanceof WebApplicationException ) { //NICE way to combine both of methods, say it in the blog 
			objectDataModel.setStatus(((WebApplicationException)ex).getResponse().getStatus());
		} else {
			objectDataModel.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()); //defaults to internal server error 500
		}
	}
}