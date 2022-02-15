package com.encuesta.models.responses;

import lombok.Data;

@Data
public class UserRest {

    /*
     * devolvemos representacion del objeto User q
     * queremos retornar a la hora de crear un usuario.
     * contiene solo los campos q nosotros queremos retornar del objeto usuario
     */

    private int id;

    private String name;

    private String email;


}
