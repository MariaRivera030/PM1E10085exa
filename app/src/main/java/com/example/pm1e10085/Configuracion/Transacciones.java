package com.example.PM1E10085.Configuracion;

public class Transacciones
{
    //nombre de la base de datos

    public static final String NameDatabase="PM1E1";

    //tabla base de datos

    public static final  String tablaContactos="contactos";

    //campos de la tabla personas

    public static final String id="id";
    public static final String pais="pais";
    public static final String nombre="nombre";
    public static final String telefono="telefono";
    public static final String nota="nota";
    public static final String imagen="imagen";

    //DDL crear y eliminar

    public static final String CreateTableContacto = "CREATE TABLE CONTACTOS"+
            "( id INTEGER PRIMARY KEY AUTOINCREMENT, pais TEXT, nombre TEXT, telefono INTEGER, "+
            "nota TEXT, imagen BLOB)";
    public static final String DROPTableContacto="DROP TABLE IF EXISTS contactos";

    public static final String SelectTableContacto="SELECT * FROM "+ Transacciones.tablaContactos;


}
