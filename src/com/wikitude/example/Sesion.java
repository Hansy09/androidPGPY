package com.wikitude.example;

import java.util.ArrayList;

/**
 * 
 * @Nombre SMD
 * @Fecha 01/03/2013
 * @Descripcion Clase modelo de Sesion
 *
 */
public class Sesion {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	public ArrayList<PuntoDeInteres> getMisPDI(){
		return misPDI;
	}

	public void setMisPDI(ArrayList<PuntoDeInteres> PDI){
		this.misPDI = PDI;
	}
	
	public Sesion(String correo, String contrasenia){
		setCorreo(correo);
		setContrasenia(contrasenia);
	}
	
	public Sesion(){
		
	}
	
	int id = 0;
	String correo = "";
	String contrasenia = "";
	ArrayList<PuntoDeInteres> misPDI = new ArrayList<PuntoDeInteres>();;
}
