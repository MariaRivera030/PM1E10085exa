package com.example.PM1E10085.Models;

public class Contactos {

    private Integer id_contacto;
    private String pais;
    private String nombre;
    private Integer telefono;
    private String nota;
    private String foto;



    public Contactos(Integer id_contacto, String pais, String nombre, Integer telefono, String nota, String foto) {
        this.id_contacto = id_contacto;
        this.pais = pais;
        this.nombre = nombre;
        this.telefono = telefono;
        this.nota = nota;
        this.foto = foto;
    }

    public Contactos() {

    }

    public Integer getId_contacto() {
        return id_contacto;
    }

    public void setId_contacto(Integer id_contacto) {
        this.id_contacto = id_contacto;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }




}
