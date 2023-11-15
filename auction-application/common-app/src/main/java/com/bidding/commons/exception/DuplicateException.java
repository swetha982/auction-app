package com.bidding.commons.exception;

import com.mindstix.web.rest.baseline.common.model.Error;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String s) {
        super(s);
    }

    public DuplicateException(Error error){
        super(error.getMessage());
    }


}
