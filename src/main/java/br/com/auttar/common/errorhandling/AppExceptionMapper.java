package br.com.auttar.common.errorhandling;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.auttar.common.model.ObjectDataModel;
import br.com.auttar.common.util.StringUtils;
import br.com.auttar.common.constants.LogConstants;
import br.com.auttar.common.errorhandling.AppException;

@Provider
public class AppExceptionMapper implements ExceptionMapper<AppException> {

	private Logger logger = LogManager.getLogger(LogConstants.ERRORHANDLING_LOGGER_NAME);
	
	public AppExceptionMapper(){}
	
	public Response toResponse(AppException ex) {

    	logger.error(ex.getMessage(), ex);
		
		ObjectDataModel<?> objectDataModel = new ObjectDataModel<>();
		objectDataModel.setStatus(ex.getStatus());
		objectDataModel.setCode(ex.getCode());
		objectDataModel.setMessage(StringUtils.formatForPOS(ex.getMessage()));
		
		return Response.status(ex.getStatus())
				.entity(objectDataModel)
				.type(MediaType.APPLICATION_JSON).
				build();
	}

}