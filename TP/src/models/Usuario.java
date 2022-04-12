package models;

import java.io.Serializable;
import java.util.HashMap;

public class Usuario implements Serializable {

	private String nombre;
	private String contrasenia;

	public Usuario() {
	}

	public Usuario(String nombre, String constrasenia) {
		this.nombre = nombre;
		this.contrasenia = constrasenia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia() {
		this.contrasenia = contrasenia;
	}
}