package com.heycarlight.exceptions;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler that converts thrown exceptions to readable error responses.
 */
@ControllerAdvice("com.heycarlight.controllers")
class ErrorControllerAdvice {

    private static Logger LOGGER = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    @ExceptionHandler(NonExistingEntity.class)
    public ResponseEntity<?> handleEntityNotFoundException(NonExistingEntity exception) {
        return this.buildErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CSVParsing.class)
    public ResponseEntity<?> handleCSVParsingException(CSVParsing exception) {
        return this.buildErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> buildErrorResponse(String message, HttpStatus status) {
        LOGGER.error("Error raised: " + message + " - Status: " + status.getReasonPhrase());
        return new ResponseEntity<>(JsonErrorResponse.create(message,status.getReasonPhrase()), status);
    }

    private static class JsonErrorResponse {
        public static ObjectNode create(String message, String status) {
            ObjectNode jsonNode = JsonNodeFactory.instance.objectNode();
            jsonNode.put("message", message);
            jsonNode.put("status", status);
            return jsonNode;
        }
    }
}