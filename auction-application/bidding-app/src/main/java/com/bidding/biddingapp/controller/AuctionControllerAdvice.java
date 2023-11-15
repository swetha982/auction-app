package com.bidding.biddingapp.controller;

import com.bidding.commons.exception.DuplicateException;
import com.bidding.commons.exception.NoDataFoundException;
import com.mindstix.web.rest.baseline.common.model.ApiResponse;
import com.mindstix.web.rest.baseline.common.model.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class AuctionControllerAdvice {

    @ExceptionHandler(DuplicateException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleDuplicateException(HttpServletRequest request, DuplicateException ex) {
        log.info("Controller Advice");
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        System.out.println("Ex:"+ex);
        //apiResponse.setData(ex.getMessage());
        apiResponse.setError(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(NoDataFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Object> handleNoDataFoundException(HttpServletRequest request, NoDataFoundException ex) {
        log.info("Controller Advice");
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        System.out.println("Ex:"+ex);
        //apiResponse.setData(ex.getMessage());
        apiResponse.setError(ex.getMessage());
        return apiResponse;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleGeneralException(HttpServletRequest req, Exception e) {
        log.info("Controller Advice for General Exception");
        //log.error(ERROR_STRING, e);
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        List<Error> errors = new ArrayList<>();
        Error errorObject = new Error();
        errorObject.setError(HttpStatus.INTERNAL_SERVER_ERROR.name());
        errorObject.setMessage(e.getLocalizedMessage());
        errors.add(errorObject);
        apiResponse.setErrors(errors);
        log.info(String.valueOf(apiResponse));
        return apiResponse;
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Object> noHandlerFoundException(HttpServletRequest req, Exception e) {
        log.info("Controller Advice for NoHandlerFoundException");
        log.info("Exception:"+e);
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        List<Error> errors = new ArrayList<>();
        Error errorObject = new Error();
        errorObject.setError(HttpStatus.NOT_FOUND.name());
        errorObject.setMessage("Requested resource not found");
        errors.add(errorObject);
        apiResponse.setErrors(errors);
        log.info(String.valueOf(apiResponse));
        return apiResponse;
    }

}
