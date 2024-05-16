package com.example.wikirick.Personajes;

public class PersonajesData {


    private String nombrePersonaje;
    private String imageUrl;
    private String next;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    private String prev;
    private String genero;

    private String estado;
    private String origen;
    private String especie;
    //nombre,genero,estado,origen,especie,episodios.


    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    public void setNombrePersonaje(String nombrePersonaje) {
        this.nombrePersonaje = nombrePersonaje;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public PersonajesData(String nombrePersonaje, String imageUrl, String genero, String estado, String origen, String especie,String next,String prev) {
        this.nombrePersonaje = nombrePersonaje;
        this.imageUrl = imageUrl;
        this.genero = genero;
        this.estado = estado;
        this.origen = origen;
        this.especie = especie;
        this.next=next;
        this.prev=prev;
    }
}
