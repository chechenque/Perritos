package com.example.luisangel.perritos;

public class Perrito {
    String name;
    String imagen;

    public Perrito(String name, String imagen) {
        this.name = name;
        this.imagen = imagen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
