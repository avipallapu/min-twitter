package com.challenge.domain;


public class ChallengeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5848153321064042161L;

	public ChallengeException() {
        // TODO Auto-generated constructor stub
    }

    public ChallengeException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public ChallengeException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public ChallengeException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }
}
