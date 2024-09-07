package com.todo.todolist.util.handler;

import com.todo.todolist.dto.ResponseDTO;
import com.todo.todolist.util.exception.BusinessException;
import com.todo.todolist.util.exception.ErrorDTO;
import com.todo.todolist.util.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String MSG_ERRO = "Error: ";

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDTO<ErrorDTO>> businessException(BusinessException exception,
                                                                      HttpServletRequest request) {

        ErrorDTO err = new ErrorDTO(
                ZonedDateTime.now(),
                exception.getHttpStatusCode().value(),
                "Business error",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO<ErrorDTO>(
                false,
                MSG_ERRO + exception.getMessage(),
                null,
                err));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<ErrorDTO>> handleValidationExceptions(MethodArgumentNotValidException exception, HttpServletRequest request) {
        StringBuilder errorMessage = new StringBuilder("Validation Error: ");

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessage.append(fieldName).append(": ").append(message).append("; ");
        });

        ErrorDTO err = new ErrorDTO(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                errorMessage.toString(),
                request.getRequestURI());

        return new ResponseEntity<>(new ResponseDTO<>(false, MSG_ERRO + exception.getMessage(), null, err), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO<ErrorDTO>> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
        ErrorDTO err = new ErrorDTO(
                ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO<>(false, MSG_ERRO + exception.getMessage(), null, err));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO<ErrorDTO>> handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
        var err = new ErrorDTO(
                ZonedDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDTO<>(false, MSG_ERRO + exception.getMessage(), null, err));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<ErrorDTO>> internalErrorException(Exception exception, HttpServletRequest request) {

        ErrorDTO err = new ErrorDTO(
                ZonedDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected problem occurred.",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO<>(
                false,
                MSG_ERRO + exception.getMessage(),
                null,
                err));
    }

}