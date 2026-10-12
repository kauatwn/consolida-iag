package br.com.consolidaiag.shared;

import br.com.consolidaiag.catalog.domain.exception.ProductAlreadyExistsException;
import br.com.consolidaiag.catalog.domain.exception.ProductNotFoundException;
import br.com.consolidaiag.cycle.domain.exceptions.ActiveCycleAlreadyExistsException;
import br.com.consolidaiag.cycle.domain.exceptions.CycleAlreadyClosedException;
import br.com.consolidaiag.cycle.domain.exceptions.InvalidCycleDateException;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.ActiveCycleNotFoundException;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.ClosedCycleImmutableException;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.DuplicateRequestForCycleException;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.InactiveProductSelectionException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleProductAlreadyExistsException(ProductAlreadyExistsException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleProductNotFoundException(ProductNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(ActiveCycleAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleActiveCycleAlreadyExistsException(ActiveCycleAlreadyExistsException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(CycleAlreadyClosedException.class)
    public ResponseEntity<ApiErrorResponse> handleCycleAlreadyClosedException(CycleAlreadyClosedException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(ActiveCycleNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleActiveCycleNotFoundException(ActiveCycleNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(ClosedCycleImmutableException.class)
    public ResponseEntity<ApiErrorResponse> handleClosedCycleImmutableException(ClosedCycleImmutableException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage(), request);
    }

    @ExceptionHandler(DuplicateRequestForCycleException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateRequestForCycleException(DuplicateRequestForCycleException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(InactiveProductSelectionException.class)
    public ResponseEntity<ApiErrorResponse> handleInactiveProductSelectionException(InactiveProductSelectionException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidCycleDateException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidCycleDateException(InvalidCycleDateException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {

        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        String message = "Validation failed: " + errors;
        return buildResponse(HttpStatus.BAD_REQUEST, message, request);
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message, HttpServletRequest request) {
        ApiErrorResponse body = ApiErrorResponse.of(request, status, message);
        return ResponseEntity.status(status).body(body);
    }
}
