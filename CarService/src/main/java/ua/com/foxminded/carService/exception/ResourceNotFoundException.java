package ua.com.foxminded.carService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6775058554866919571L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
