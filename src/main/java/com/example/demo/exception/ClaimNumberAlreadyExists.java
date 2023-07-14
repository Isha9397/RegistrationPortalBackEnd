package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.CONFLICT, reason="The Claim Number already present in Database")
public class ClaimNumberAlreadyExists extends Exception{

}
