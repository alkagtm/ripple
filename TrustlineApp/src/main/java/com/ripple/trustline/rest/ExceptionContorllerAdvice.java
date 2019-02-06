package com.ripple.trustline.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ripple.trustline.data.TransferErroResponse;

@ControllerAdvice
public class ExceptionContorllerAdvice {
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<TransferErroResponse> exceptionHandler(Exception ex) {
		TransferErroResponse error = new TransferErroResponse();
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("TrustLine TransferService Failed");
        return new ResponseEntity<TransferErroResponse>(error, HttpStatus.BAD_REQUEST);
    }

}
