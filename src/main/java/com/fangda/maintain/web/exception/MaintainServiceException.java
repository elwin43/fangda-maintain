package com.fangda.maintain.web.exception;

public class MaintainServiceException extends RuntimeException {
    static final long serialVersionUID = 1L;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public MaintainServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaintainServiceException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public MaintainServiceException(Throwable cause) {
        this(null, cause);
    }

    public MaintainServiceException(String code, String message) {
        this(code, message, null);
    }

}