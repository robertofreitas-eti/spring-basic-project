package br.eti.freitas.basicproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RestServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String serviceName;
	private HttpStatus statusCode;
	private String error;

	public RestServiceException() {
	}
	
	public RestServiceException(String serviceName, HttpStatus statusCode, String error) {
		this.serviceName = serviceName;
		this.statusCode = statusCode;
		this.error = error;
	}

	public RestServiceException(HttpStatus statusCode, String error) {
		this.statusCode = statusCode;
		this.error = error;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
