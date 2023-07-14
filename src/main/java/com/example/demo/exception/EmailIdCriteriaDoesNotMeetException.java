package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="Emaild Id should contain (@) and (.) ")
public class EmailIdCriteriaDoesNotMeetException extends Exception{

}
