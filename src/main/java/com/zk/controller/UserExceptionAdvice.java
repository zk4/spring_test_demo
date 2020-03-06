package com.zk.controller;

import com.zk.exception.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionAdvice {
	@ExceptionHandler(UserNotFound.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleNonExistingHero() {
		System.out.println("user not found");
	}
}
