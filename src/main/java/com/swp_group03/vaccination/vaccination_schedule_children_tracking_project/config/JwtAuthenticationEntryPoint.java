package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.exception.ErrorCode;
import com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.model.response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errCode = ErrorCode.UNAUTHORIZED;

        response.setStatus(errCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errCode.getCode())
                .message(errCode.getMessage())
                .build();

        ObjectMapper mapper = new ObjectMapper();

        response.getWriter().print(mapper.writeValueAsBytes(apiResponse));
        response.flushBuffer();
    }
}
