package com.alura.foroHub.domain.curso;

public enum Tipo {

    COCINA("Cocina"),
    DEPORTES("Deportes");

    private String tipo;

    Tipo(String tipo){
        this.tipo = tipo;
    }

    public static Tipo fromString(String text){
        for (Tipo tipo : Tipo.values()){
            if (tipo.tipo.equalsIgnoreCase(text)){
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se encontro ese tipo " + text);
    }

}
