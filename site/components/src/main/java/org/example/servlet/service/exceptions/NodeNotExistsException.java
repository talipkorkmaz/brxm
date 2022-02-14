package org.example.servlet.service.exceptions;

public class NodeNotExistsException extends RuntimeException{
	public NodeNotExistsException(String errorMessage) {
		super(errorMessage);
	}

}
