package com.github.bitcoin.handlers;

import com.github.bitcoin.exceptions.SendTransactionFailed;
import com.github.bitcoin.payload.Error;
import com.github.facade.bitcoin.exceptions.ErrorFee;
import com.github.facade.bitcoin.exceptions.NotEnoughMoney;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Error> handlerNotFound(NotEnoughMoney ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new Error(status.value(), ex.getMessage()), status);
    }

    @ExceptionHandler
    public ResponseEntity<Error> handlerBadRequest(ErrorFee ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new Error(
                status.value(), ex.getMessage()), status);
    }

    @ExceptionHandler
    public ResponseEntity<Error> handlerSendTrxFailed(SendTransactionFailed ex) {
        HttpStatus status = HttpStatus.GONE;
        return new ResponseEntity<>(
                new Error(status.value(), ex.getMessage()), status
        );
    }

}
