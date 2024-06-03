package com.example.wikirick.Personajes;

import java.util.ArrayList;
import java.util.List;

public class PersonajesData {





    private String id;
    private String nombrePersonaje;
    private String imageUrl;
    private String next;
    private String prev;
    private String genero;

    private String estado;
    private String origen;
    private String especie;
    private ArrayList<String> episodes;





    public ArrayList<String> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<String> episodes) {
        this.episodes = episodes;
    }
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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





    public PersonajesData(String id, String nombrePersonaje, String imageUrl, String next, String prev, String genero, String estado, String origen, String especie, ArrayList<String> episodes) {
        this.id = id;
        this.nombrePersonaje = nombrePersonaje;
        this.imageUrl = imageUrl;
        this.next = next;
        this.prev = prev;
        this.genero = genero;
        this.estado = estado;
        this.origen = origen;
        this.especie = especie;
        this.episodes = episodes;
    }
}