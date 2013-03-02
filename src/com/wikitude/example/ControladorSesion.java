package com.wikitude.example;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * @Nombre SMD
 * @Fecha 01/03/2013
 * @Descripcion Clase controladora de la sesion, realiza las
 * funciones de la sesion y devuelve los valores a los visuales
 * y a los modelos
 *
 */
public class ControladorSesion{
	
	private ControladorSesion(){
		
	}
	
	public static ControladorSesion getInstance(){
		if (controladorSesion==null){
			controladorSesion = new ControladorSesion();
		}
		return controladorSesion;
	}
	
	
	public boolean confirmarContrasenia(String cont1, String cont2){
		if(cont1.equals(cont2))
			return true;
		else return false;
	}
	
	public boolean estaSesionIniciada(){
		return sesionIniciada;
	}
	
	public void registrarUsuario(Sesion sesion, RegistrarUsuarioActivity act){
		GestorServer gestor = new GestorServer();
		gestor.registrarUsuarioEnServidor(sesion, act);
	}
	
	public void iniciarSesion(Sesion sesion, IniciarSesionActivity act){
		this.sesion.setCorreo(sesion.getCorreo());
		this.sesion.setContrasenia(sesion.getContrasenia());
		GestorServer gestor = new GestorServer();
		gestor.verificarInicioSesionEnServidor(sesion, act);
	}
	
	public void cerrarSesion(){
		setSesionIniciada(false);
		sesion = new Sesion();
	}
	
	public boolean validarDatosCompletosRegistro(String correo, String cont1, String cont2){
		if(correo.equals("")|| cont1.equals("") || cont2.equals(""))
			return false;
		else return true;
	}
	
	public boolean validarDatosCompletosInicio(String correo, String cont1){
		if(correo.equals("") || cont1.equals(""))
			return false;
		else return true;
	}
	
	public boolean getSesionIniciada() {
		return sesionIniciada;
	}

	public void setSesionIniciada(boolean sesionIniciada) {
		this.sesionIniciada = sesionIniciada;
	}

	public Sesion getSesion() {
		return sesion;
	}

	public void setSesion(Sesion sesion) {
		this.sesion = sesion;
	}
	
	private boolean sesionIniciada = false;
	private Sesion sesion = new Sesion();
	private static ControladorSesion controladorSesion;
	
}
