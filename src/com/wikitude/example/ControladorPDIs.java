package com.wikitude.example;

import java.util.ArrayList;

/**
 * Clase que controla la iterracion de los activities con el gestor del servidor
 * 
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

	public void filtrarPDIsCercanos(Posicion posicion, double distanciaMax,
			VisorInterface visor) {
		daoPDIs.buscarDentroDeRangoMax(posicion, distanciaMax, visor);
	}

	public void filtrarPDIsPorCategorias(Posicion posicion,
			double distanciaMax, String clave, String categoria,
			VisorInterface visor) {
		esBusquedaAvanzada = true;
		daoPDIs.buscarPDIsPorCategoria(posicion, distanciaMax, clave,
				categoria, visor);
	}

	public void filtrarPDIsPorNombre(Posicion posicion, double distanciaMax,
			String clave, VisorInterface visor) {
		esBusquedaSimple = true;
		daoPDIs.buscarPDIsPorCategoria(posicion, distanciaMax, clave,
				"nombre", visor);
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

	public void setDaoPDIs(GestorServer daoPDIs) {
		this.daoPDIs = daoPDIs;
	}

	public boolean esUnaBusquedaAvanzada() {
		return esBusquedaAvanzada;
	}

	public boolean esUnaBusquedaSimple() {
		return esBusquedaSimple;
	}
	
	private ArrayList<PuntoDeInteres> puntosDeInteres = new ArrayList<PuntoDeInteres>();
	private String puntosDeInteresJArray = "";
	private GestorServer daoPDIs = new GestorServer();
	private static ControladorPDIs controlador;

	private static boolean esBusquedaAvanzada = false;
	private static boolean esBusquedaSimple = false;

}
