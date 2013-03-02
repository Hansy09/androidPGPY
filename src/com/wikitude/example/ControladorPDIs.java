package com.wikitude.example;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
/**
 * Clase que controla la iterracion de los activities con el gestor del servidor
 * @author Hansy
 *
 */
public class ControladorPDIs {

	private ControladorPDIs() {

	}

	public static ControladorPDIs getInstance() {
		if (controlador == null)
			controlador = new ControladorPDIs();
		return controlador;

	}

	public void filtrarPDIsCercanos(Posicion posicion, double distanciaMax, VisorInterface visor) {
		daoPDIs.buscarDentroDeRangoMax(posicion, distanciaMax, visor);
	}
	/**
	 * Metodo que sirve para registrar un nuevo punto de interes
	 * @param pdi El punto de interes a registrar
	 * @param activity El acitivity que mostrara los mensajes del servidor
	 */
	public void registrarPDI(String usuario, PuntoDeInteres pdi,ToastInterface activity) {
		daoPDIs.registrarPDIEnServidor(usuario, pdi,activity);
	}
	
	public void actulizarPDI(String usuario, PuntoDeInteres pdi,ToastInterface activity) {
		
		daoPDIs.actualizarPDIEnServidor(usuario, pdi,activity);
	}
    public void borrarPDI(String usuario, int id,ToastInterface activity) {
		
		daoPDIs.borrarPDIenServidor(usuario, id,activity);
	}
	

	public void filtrarPDIsPorCategorias(Posicion posicion, double distanciaMax,
			String clave, String categoria, VisorInterface visor) {
		daoPDIs.buscarPDIsPorCategoria(posicion, distanciaMax, clave, categoria,visor);
	}

	public ArrayList<PuntoDeInteres> obtenerPDIs() {
		return puntosDeInteres;
	}

	
	
	public ArrayList<PuntoDeInteres> getPuntosDeInteres() {
		return puntosDeInteres;
	}

	public void setPuntosDeInteres(ArrayList<PuntoDeInteres> puntosDeInteres) {
		this.puntosDeInteres = puntosDeInteres;
	}

	public String getPuntosDeInteresJArray() {
		return puntosDeInteresJArray;
	}

	public void setPuntosDeInteresJArray(String puntosDeInteresJArray) {
		this.puntosDeInteresJArray = puntosDeInteresJArray;
	}

	public GestorServer getDaoPDIs() {
		return daoPDIs;
	}

	public double getLatitudActual() {
		return latitudActual;
	}

	public void setLatitudActual(double latitudActual) {
		this.latitudActual = latitudActual;
	}

	public double getLongitudActual() {
		return longitudActual;
	}

	public void setLongitudActual(double longitudActual) {
		this.longitudActual = longitudActual;
	}

	public double getAltitudActual() {
		return altitudActual;
	}

	public void setAltitudActual(double altitudActual) {
		this.altitudActual = altitudActual;
	}

	public void setDaoPDIs(GestorServer daoPDIs) {
		this.daoPDIs = daoPDIs;
	}



	private ArrayList<PuntoDeInteres> puntosDeInteres = new ArrayList<PuntoDeInteres>();
	private String puntosDeInteresJArray = "";
	private GestorServer daoPDIs = new GestorServer();
	private static ControladorPDIs controlador;
	private  double longitudActual = 13.069730f;
	private  double latitudActual = 47.77318f;
	private double altitudActual=0;
	

}
