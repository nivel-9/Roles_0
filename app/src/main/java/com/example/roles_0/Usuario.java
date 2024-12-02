package com.example.roles_0;

public class Usuario {
    private int id;
    private String nombre;
    private String genero;
    private String fechaNacimiento;
    private String nivelEstudios;
    private String intereses;
    private String telefono;
    private String usuario;
    private String contraseña;

    public Usuario(int id, String nombre, String genero, String fechaNacimiento, String nivelEstudios, String intereses, String telefono, String usuario, String contraseña) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.nivelEstudios = nivelEstudios;
        this.intereses = intereses;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNivelEstudios() {
        return nivelEstudios;
    }

    public String getIntereses() {
        return intereses;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public int getId() {
        return id;
    }
}