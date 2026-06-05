package Esports.Reglas_Torneo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReglaTorneoNotConnected.class)
    public ResponseEntity<?> manejoReglaTorne(ReglaTorneoNotConnected e){
        HashMap<String, Object> error = new HashMap<>();
        error.put("estado",503);
        error.put("mensaje", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(error);
    }





}
