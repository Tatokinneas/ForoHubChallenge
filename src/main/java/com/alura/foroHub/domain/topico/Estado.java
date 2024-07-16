package com.alura.foroHub.domain.topico;

public enum Estado {

    POSTEADO("Posteado"),
    PENDIENTE("Pendiente");

    private String estado;

    Estado(String estado){
        this.estado = estado;
    }

    public static Estado fromString(String text){
        for(Estado estado : Estado.values()){
            if(estado.estado.equalsIgnoreCase(text)){
                return estado;
            }
        }
        throw new IllegalArgumentException("Ningún estado encontrado: " + text);
    }

}
