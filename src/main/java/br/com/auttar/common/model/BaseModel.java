package br.com.auttar.common.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import br.com.auttar.common.constants.LogConstants;

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class BaseModel {
	
	private Logger logger = LogManager.getLogger(LogConstants.MODEL_LOGGER_NAME);

	public String toJsonString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.registerModule(new JaxbAnnotationModule());
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
	    	logger.error(e.getMessage(), e);
			return null;
		}
	}
	
}
