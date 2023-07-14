package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="Contact Number Should Be 11 Digits")
public class ContactNoGreaterThanReqException extends Exception{

}
