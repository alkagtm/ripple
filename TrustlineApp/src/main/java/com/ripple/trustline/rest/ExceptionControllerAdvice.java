package com.ripple.trustline.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ripple.trustline.data.TransferErroResponse;
import com.ripple.trustline.service.TrustLineService;

@ControllerAdvice
public class ExceptionControllerAdvice {
	private static final Logger log = LogManager.getLogger(TrustLineService.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<TransferErroResponse> exceptionHandler(Exception ex) {
		log.info("TransferService Failed");
		TransferErroResponse error = new TransferErroResponse();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage("TrustLine TransferService Failed");
		return new ResponseEntity<TransferErroResponse>(error, HttpStatus.BAD_REQUEST);
	}

}
