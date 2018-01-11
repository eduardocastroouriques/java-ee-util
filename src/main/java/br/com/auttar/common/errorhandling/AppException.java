package br.com.auttar.common.errorhandling;

import javax.ws.rs.core.Response.Status;

public class AppException extends Exception {

	private static final long serialVersionUID = -8999932578270387947L;
	
	Integer status;
	int code; 
	
	public AppException(int status, int code, String message) {
		super(message);
		this.status = status;
		this.code = code;
	}
	
	public AppException(Status status, int code, String message) {
		super(message);
		this.status = status.getStatusCode();
		this.code = code;
	}

	public AppException() { }


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
					
}