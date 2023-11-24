package com.lcwd.electronic.store.electronicStore.exceptions;

import com.lcwd.electronic.store.electronicStore.dtos.ApiResponce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //handler resource not found exception
    private Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponce> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        logger.info("Global Exception Handler Involved sucessfully!!");
        ApiResponce responce = ApiResponce.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).sucess(true).build();
        return new ResponseEntity(responce,HttpStatus.NOT_FOUND);
    }
    //method argumrnt not valid exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String ,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String, Object> responce = new HashMap<>();
        allErrors.stream().forEach(objectError -> {
            String msg = objectError.getDefaultMessage();
            String feild=((FieldError) objectError).getField();
            responce.put(feild,msg);
        });
    return new ResponseEntity(responce,HttpStatus.BAD_REQUEST);
    }

}
