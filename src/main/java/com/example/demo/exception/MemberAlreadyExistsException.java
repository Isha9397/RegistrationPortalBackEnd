package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.CONFLICT, reason="The Member Id is already present in Database")
public class MemberAlreadyExistsException extends Exception{

}
