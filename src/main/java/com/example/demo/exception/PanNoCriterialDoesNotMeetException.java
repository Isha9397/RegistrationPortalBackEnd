package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="Pan Number Criteria Does Not Meet")
public class PanNoCriterialDoesNotMeetException extends Exception{

}
