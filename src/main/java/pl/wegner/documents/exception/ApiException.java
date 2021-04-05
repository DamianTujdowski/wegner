package pl.wegner.documents.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
class ApiException {

    private HttpStatus status;
    private String exception;
    private String message;
    private String path;

}
