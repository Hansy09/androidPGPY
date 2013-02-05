package com.wikitude.example;

import java.util.ArrayList;

import org.json.JSONObject;

public class ControladorPDIs {

	public void filtrarPDIsCercanos(double distanciaMax) {
		//puntosDeInteres=puntosDeInteresDaoPDIs.buscarDentroDeRangoMax(distanciaMax);
	}

	public void filtrarPDIsPorCategorias(double distanciaMax,
			ArrayList<String> categorias) {
		//puntosDeInteres=puntosDeInteresDaoPDIs.buscarPDIsPorCategoria(distanciaMax,categorias); 
	}

	public ArrayList<PuntoDeInteres> obtenerPDIs() {
		return puntosDeInteres;
	}

	private ArrayList<PuntoDeInteres> puntosDeInteres = new ArrayList<PuntoDeInteres>();
	

}
