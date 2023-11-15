package com.bidding.commons.exception;

import com.mindstix.web.rest.baseline.common.model.Error;

public class NoDataFoundException extends RuntimeException{
    public NoDataFoundException(String s) {
        super(s);
    }

    public NoDataFoundException(Error error){
        super(error.getMessage());
    }
}
