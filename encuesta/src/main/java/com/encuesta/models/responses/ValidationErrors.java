package com.encuesta.models.responses;

import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor /* se nos genera constructor con todos los argumentos */
public class ValidationErrors {
    
    private Map <String, String> errors;

    private Date timestamp; // cuando se genero

}
