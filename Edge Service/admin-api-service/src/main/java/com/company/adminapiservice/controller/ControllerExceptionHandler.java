package com.company.adminapiservice.controller;

import com.company.adminapiservice.exception.*;
import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> purchaseViewModelValidationError(MethodArgumentNotValidException e,
                                                                      WebRequest request){
        // BindingResult holds the validation errors
        BindingResult result = e.getBindingResult();
        // Validation errors are stored in FieldError objects
        List<FieldError> fieldErrors = result.getFieldErrors();

        // Translate the FieldErrors to VndErrors
        List<VndErrors.VndError> vndErrorList = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            VndErrors.VndError vndError = new
                    VndErrors.VndError(request.toString(), fieldError.getDefaultMessage());
            vndErrorList.add(vndError);
        }

        // Wrap all of the VndError objects in a VndErrors object
        VndErrors vndErrors = new VndErrors(vndErrorList);

        // Create and return the ResponseEntity
        ResponseEntity<VndErrors> responseEntity = new
                ResponseEntity<>(vndErrors, HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus( HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> notAvailable(IllegalArgumentException e, WebRequest request){

        VndErrors error = new VndErrors(request.toString(), e.getMessage());

        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;

    }

    @ExceptionHandler(value = {JsonParseException.class})
    @ResponseStatus( HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<VndErrors> missingArgumentsException(JsonParseException e, WebRequest request){

        VndErrors error = new VndErrors(request.toString(), e.getMessage());

        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        return responseEntity;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @ExceptionHandler(value = {CustomerNotFoundException.class})
    @ResponseStatus( HttpStatus.NOT_FOUND)
    public ResponseEntity<VndErrors> customerNotFoundException(CustomerNotFoundException e, WebRequest request){

        VndErrors error = new VndErrors(request.toString(), e.getMessage());

        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(value = {DeleteNotAllowedException.class})
    @ResponseStatus( HttpStatus.FORBIDDEN)
    public ResponseEntity<VndErrors> deleteNotAllowedException(DeleteNotAllowedException e, WebRequest request){

        VndErrors error = new VndErrors(request.toString(), e.getMessage());

        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        return responseEntity;
    }

    @ExceptionHandler(value = {InventoryNotFoundException.class})
    @ResponseStatus( HttpStatus.NOT_FOUND)
    public ResponseEntity<VndErrors> inventoryNotFoundException(InventoryNotFoundException e, WebRequest request){

        VndErrors error = new VndErrors(request.toString(), e.getMessage());

        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(value = {InvoiceNotFoundException.class})
    @ResponseStatus( HttpStatus.NOT_FOUND)
    public ResponseEntity<VndErrors> invoiceNotFoundException(InvoiceNotFoundException e, WebRequest request){

        VndErrors error = new VndErrors(request.toString(), e.getMessage());

        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(value = {LevelUpNotFoundException.class})
    @ResponseStatus( HttpStatus.NOT_FOUND)
    public ResponseEntity<VndErrors> levelUpNotFoundException(LevelUpNotFoundException e, WebRequest request){

        VndErrors error = new VndErrors(request.toString(), e.getMessage());

        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(value = {ProductNotFoundException.class})
    @ResponseStatus( HttpStatus.NOT_FOUND)
    public ResponseEntity<VndErrors> productNotFoundException(ProductNotFoundException e, WebRequest request){

        VndErrors error = new VndErrors(request.toString(), e.getMessage());

        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler(value = {UpdateNotAllowedException.class})
    @ResponseStatus( HttpStatus.FORBIDDEN)
    public ResponseEntity<VndErrors> updateNotAllowedException(UpdateNotAllowedException e, WebRequest request){

        VndErrors error = new VndErrors(request.toString(), e.getMessage());

        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        return responseEntity;
    }

    @ExceptionHandler(value = {OutOfStockException.class})
    @ResponseStatus( HttpStatus.FORBIDDEN)
    public ResponseEntity<VndErrors> outOfStockException(OutOfStockException e, WebRequest request){

        VndErrors error = new VndErrors(request.toString(), e.getMessage());

        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        return responseEntity;
    }

    @ExceptionHandler(value = {OrderProcessFailException.class})
    @ResponseStatus( HttpStatus.FORBIDDEN)
    public ResponseEntity<VndErrors> orderProcessFailException(OrderProcessFailException e, WebRequest request){

        VndErrors error = new VndErrors(request.toString(), e.getMessage());

        ResponseEntity<VndErrors> responseEntity = new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        return responseEntity;
    }

}
