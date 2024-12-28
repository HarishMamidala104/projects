package com.ojas.hiring.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(BaseException.class);
    private String exceptionMsg;
    private int code;
    private Object responseData;

     
   

    /**
     * @param message
     * @param code
     */
    public BaseException(String message, int code) {
        super(message);
        this.code = code;
        this.exceptionMsg = message;
       
    }

    public BaseException(BaseErrors baseErrors) {
        super(baseErrors.getMessage());
        this.code = baseErrors.getCode();
        this.exceptionMsg = baseErrors.getMessage();
      
    }

    
}
