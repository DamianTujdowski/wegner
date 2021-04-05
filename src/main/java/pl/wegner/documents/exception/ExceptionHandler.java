package pl.wegner.documents.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiException apiException = createApiValidationException(ex, request);
        return new ResponseEntity<>(apiException, headers, apiException.getStatus());
    }

    private ApiException createApiValidationException(MethodArgumentNotValidException exception, WebRequest request) {
        return new ApiException(HttpStatus.BAD_REQUEST,
                exception.getClass().getSimpleName(),
                getExceptionMessages(exception),
                request.getDescription(false));
    }

    private String getExceptionMessages(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::getFieldExceptionMessage)
                .collect(Collectors.joining("\n"));
    }

    private String getFieldExceptionMessage(ObjectError error) {
        String fieldName = ((FieldError) error).getField();
        String message = error.getDefaultMessage();
        return String.format("%s: %s", fieldName, message);
    }

}
