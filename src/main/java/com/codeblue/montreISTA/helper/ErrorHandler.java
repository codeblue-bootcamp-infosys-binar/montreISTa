package com.codeblue.montreISTA.helper;

import com.codeblue.montreISTA.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<Object> showCustomMessage(Exception e){

        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,"Please Check your JSON input");

    }
}

