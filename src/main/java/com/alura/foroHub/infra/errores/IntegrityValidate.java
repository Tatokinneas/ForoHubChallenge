package com.alura.foroHub.infra.errores;

public class IntegrityValidate extends RuntimeException{

    public IntegrityValidate(String s){
        super(s);
    }

}
