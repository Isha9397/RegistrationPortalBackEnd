package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="Password Criteria Does Not Meet")
public class PasswordCriteriaDoesNotMeetException extends Exception{

}
