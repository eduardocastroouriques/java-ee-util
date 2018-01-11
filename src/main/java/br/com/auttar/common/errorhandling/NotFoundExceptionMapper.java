package br.com.auttar.common.errorhandling;

import com.sun.jersey.api.NotFoundException;

import br.com.auttar.common.constants.AppConstants;
import br.com.auttar.common.constants.LogConstants;
import br.com.auttar.common.model.ObjectDataModel;
import br.com.auttar.common.util.StringUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
	
	private Logger logger = LogManager.getLogger(LogConstants.ERRORHANDLING_LOGGER_NAME);
	
	public NotFoundExceptionMapper(){}
	
	public Response toResponse(NotFoundException ex) {

    	logger.error(ex.getMessage(), ex);
		
		ObjectDataModel<?> objectDataModel = new ObjectDataModel<>();
		objectDataModel.setCode(AppConstants.RESPONSE_CODE_NOT_FOUND);
		objectDataModel.setStatus(Response.Status.NOT_FOUND.getStatusCode());
		objectDataModel.setMessage(StringUtils.formatForPOS(ex.getMessage()));
		
		return Response.status(ex.getResponse().getStatus())
				.entity(objectDataModel)
				.type(MediaType.APPLICATION_JSON) //this has to be set to get the generated JSON 
				.build();
	}

}
