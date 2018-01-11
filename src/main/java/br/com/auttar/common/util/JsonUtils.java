package br.com.auttar.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import br.com.auttar.common.constants.LogConstants;
import br.com.auttar.common.errorhandling.AppException;


@ApplicationScoped
public class JsonUtils {

	private static ObjectMapper singletonObjectMapper = null;
	private static Logger logger = LogManager.getLogger(LogConstants.UTIL_LOGGER_NAME);

	public static ObjectMapper getSingletonObjectMapper() {
		if (singletonObjectMapper == null) {
			singletonObjectMapper = new ObjectMapper();
			singletonObjectMapper.registerModule(new JaxbAnnotationModule());
		}
		return singletonObjectMapper;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> jsonStringToMap(String json) throws AppException {
		HashMap<String, Object> result;
		try {
			result = getSingletonObjectMapper().readValue(json, HashMap.class);
		} catch (IOException e) {
	    	logger.error(e.getMessage(), e);
			throw new AppException(); 
		}
		return result;
	}

}
