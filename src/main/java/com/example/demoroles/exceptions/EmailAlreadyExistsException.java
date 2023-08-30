package com.example.demoroles.exceptions;

//import java.util.Arrays;
//import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailAlreadyExistsException extends ResponseStatusException {

    public EmailAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, message);
    }

//    @Override
//    public HttpHeaders getResponseHeaders(){ //deprecated
//        HttpHeaders headers = getResponseHeaders();
//        //parameters: (String Key, List<String> Value)
//        headers.put("mensage", Arrays.asList(" ejemplo"));
//        return headers;
//    }

}
