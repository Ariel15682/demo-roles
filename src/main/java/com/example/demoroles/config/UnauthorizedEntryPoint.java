package com.example.demoroles.config;

import jakarta.servlet.ServletException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import java.io.OutputStream;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;


@Component //@Component("unauthorizedEntryPoint")
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        System.out.println("Ejecutando UnauthorizedEntryPoint");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: No autorizado");

//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//        final Map<String, Object> data = new HashMap<>();
//        data.put("timestamp", new Date());
//        data.put("status", HttpServletResponse.SC_UNAUTHORIZED);
//        data.put("error", "Unauthorized");
//        data.put("message", "Access Denied, Bad Credentials!!" + authException.getMessage() );
//        data.put("path", request.getRequestURL().toString());
//
//        OutputStream out = response.getOutputStream();
//        final ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(out, data);
//        out.flush();
    }

}