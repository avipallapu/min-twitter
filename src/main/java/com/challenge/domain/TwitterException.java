package com.challenge.domain;


public class TwitterException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6954595626931106132L;
	private String errorMessage;

	public String getErrorMessage() {
        return errorMessage;
    }

	public TwitterException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

	public TwitterException() {
        super();
    }
}
