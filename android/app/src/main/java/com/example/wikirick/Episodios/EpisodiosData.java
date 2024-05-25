package com.example.wikirick.Episodios;

public class EpisodiosData {


    private String next;
    private String prev;
    private String name;
    private String date;
    private String episode;

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


    public EpisodiosData(String next, String prev, String name, String date, String episode) {
        this.next = next;
        this.prev = prev;
        this.name = name;
        this.date = date;
        this.episode = episode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }
}
