package com.auth.vibe.login.exception;
import com.auth.vibe.login.payload.ApiExpectionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobleExpection {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        Map<String,String> errorsMap = new HashMap();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String message = error.getDefaultMessage();
            String field = ((FieldError) error).getField();
            errorsMap.put(field, message);
        });
        return ResponseEntity.status(400).body(errorsMap);
    }
    @ExceptionHandler(DuplicateFieldException.class)
    public ResponseEntity<Map<String, List<String>>> handleDuplicateFieldException(DuplicateFieldException e) {
        // Create a response map
        Map<String, List<String>> response = new HashMap<>();
        response.put("message", e.getMessages());

        // Return the response with status 400
        return ResponseEntity.status(400).body(response);
    }

//    @ExceptionHandler(ResousceNotFound.class)
//    public ResponseEntity<String> handleResourceNotFound(ResousceNotFound e) {
//        return ResponseEntity.status(404).body(e.getMessage());
//    }

    @ExceptionHandler(ApiExpection.class)
    public ResponseEntity<ApiExpectionResponse> handleApiExpection(ApiExpection e) {
        return ResponseEntity.status(400).body(new ApiExpectionResponse(e.getMessage(),false));
    }


}
