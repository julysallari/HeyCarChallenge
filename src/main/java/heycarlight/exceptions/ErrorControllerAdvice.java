package heycarlight.exceptions;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler that converts thrown exceptions to readable error responses.
 */
@ControllerAdvice("heycarlight.controllers")
class ErrorControllerAdvice {

    @ExceptionHandler(NonExistingEntity.class)
    public ResponseEntity<?> handleEntityNotFoundException(NonExistingEntity exception) {
        return this.buildErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CSVParsing.class)
    public ResponseEntity<?> handleCSVParsingException(CSVParsing exception) {
        return this.buildErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> buildErrorResponse(String message, HttpStatus status) {
        ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
        jsonNode.put("message", message);
        jsonNode.put("status", status.getReasonPhrase());
        return new ResponseEntity<>(jsonNode, status);
    }
}