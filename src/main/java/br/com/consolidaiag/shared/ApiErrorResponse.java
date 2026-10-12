package br.com.consolidaiag.shared;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ApiErrorResponse(
        Instant timeStamp,
        int status,
        String error,
        String message,
        String path,
        String method
) {
    public static ApiErrorResponse of(HttpServletRequest request, HttpStatus status, String message) {
        return new ApiErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                request.getMethod()
        );
    }
}
