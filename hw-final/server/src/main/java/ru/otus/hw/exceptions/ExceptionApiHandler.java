package ru.otus.hw.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.hw.dto.ApiErrorDto;
import ru.otus.hw.utils.errors.ErrorMessage;
import ru.otus.hw.utils.formatter.HttpStatusFormatter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ExceptionApiHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorDto handleMethodEntityNotFoundException(EntityNotFoundException e) {

        ApiErrorDto apiError = ApiErrorDto.builder()
                .status(HttpStatusFormatter.formatHttpStatus(HttpStatus.NOT_FOUND))
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        log.info("handleMethodEntityNotFoundException(EntityNotFoundException e) --> {}", e);
        return apiError;

    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorDto handleValidationException(ValidationException e) {
        ApiErrorDto apiError = ApiErrorDto.builder()
                .status(HttpStatusFormatter.formatHttpStatus(HttpStatus.BAD_REQUEST))
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        log.info("handleValidationException(ValidationException e) --> {}", e);

        return apiError;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        ApiErrorDto apiError = ApiErrorDto.builder()
                .status(HttpStatusFormatter.formatHttpStatus(HttpStatus.BAD_REQUEST))
                .message(constructMessage(e))
                .timestamp(LocalDateTime.now())
                .build();
        log.info("handleMethodArgumentNotValidException(ValidationException e) --> {}", apiError);

        return apiError;
    }


    private String constructMessage(BindException e) {
        FieldError error = e.getBindingResult().getFieldError();
        assert error != null;
        return ErrorMessage.builder()
                .field(error.getField())
                .error(error.getDefaultMessage())
                .value(error.getRejectedValue())
                .build().toString();
    }

    private String constructMessage(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        return violations.stream()
                .map(violation -> new ErrorMessage(
                        violation.getPropertyPath().toString(),
                        violation.getMessage(),
                        violation.getInvalidValue()))
                .collect(Collectors.toList())
                .get(0)
                .toString();
    }

    private String constructMessage(MissingServletRequestParameterException e) {
        return ErrorMessage.builder()
                .field(e.getParameterType() + " " + e.getParameterName())
                .error(e.getMessage())
                .value(null)
                .build()
                .toString();
    }

}
