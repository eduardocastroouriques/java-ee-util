package br.com.auttar.common.model;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import br.com.auttar.common.constants.AppConstants;
import br.com.auttar.common.constants.LogConstants;
import br.com.auttar.common.util.StringUtils;

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class ObjectDataModel<T> {
	
	private Logger logger = LogManager.getLogger(LogConstants.MODEL_LOGGER_NAME);
	
	private Integer limit;
	private Integer offset;
	private Integer status;
	private Integer code;
	private String message;
	private T data;
		
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = StringUtils.formatForPOS(message);
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

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
	
	public void setSuccess(){
		this.setStatus(Status.OK.getStatusCode());
		this.setCode(AppConstants.RESPONSE_CODE_SUCCESS);
		this.setMessage("OK");
	}

}
