package br.com.consolidaiag.shared;

import br.com.consolidaiag.catalog.domain.exceptions.InvalidProductDataException;
import br.com.consolidaiag.catalog.domain.exceptions.InvalidProductStateException;
import br.com.consolidaiag.catalog.domain.exceptions.ProductAlreadyExistsException;
import br.com.consolidaiag.catalog.domain.exceptions.ProductNotFoundException;
import br.com.consolidaiag.cycle.domain.exceptions.ActiveCycleAlreadyExistsException;
import br.com.consolidaiag.cycle.domain.exceptions.CycleAlreadyClosedException;
import br.com.consolidaiag.cycle.domain.exceptions.InvalidCycleDateException;
import br.com.consolidaiag.cycle.domain.exceptions.WeeklyCycleNotFoundException;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.ActiveCycleNotFoundException;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.ClosedCycleImmutableException;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.DuplicateRequestForCycleException;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.InactiveProductSelectionException;
import br.com.consolidaiag.weeklyrequest.domain.exceptions.WeeklyRequestNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleProductAlreadyExistsException(ProductAlreadyExistsException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleProductNotFoundException(ProductNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(WeeklyCycleNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleWeeklyCycleNotFoundException(WeeklyCycleNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(WeeklyRequestNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleWeeklyRequestNotFoundException(WeeklyRequestNotFoundException ex, HttpServletRequest request) {
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
        return buildResponse(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage(), request);
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

    @ExceptionHandler(InvalidProductStateException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidProductStateException(InvalidProductStateException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidProductDataException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidProductDataException(InvalidProductDataException ex, HttpServletRequest request) {
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

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainException(DomainException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.", request);
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message, HttpServletRequest request) {
        ApiErrorResponse body = ApiErrorResponse.of(request, status, message);
        return ResponseEntity.status(status).body(body);
    }
}
