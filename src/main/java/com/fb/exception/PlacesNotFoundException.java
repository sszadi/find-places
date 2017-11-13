package com.fb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PlacesNotFoundException extends RuntimeException {

    public PlacesNotFoundException(String country, String city, String name) {
        super("Could not find place named: " + name + " in " + country + ", " + city);
    }
}
