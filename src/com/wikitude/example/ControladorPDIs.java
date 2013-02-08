package com.wikitude.example;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class ControladorPDIs {

	private ControladorPDIs() {

	}

	public static ControladorPDIs getInstance() {
		if (controlador == null)
			controlador = new ControladorPDIs();
		return controlador;

	}

	public void filtrarPDIsCercanos(double distanciaMax, VisorInterface visor) {

		
		daoPDIs.buscarDentroDeRangoMax(distanciaMax, visor);
		/*do{
			if(DaoPDI.isExisteRespuesta()){
				Gson gson = new Gson();
				List<PuntoDeInteres> myTypes = null;
				jObject = DaoPDI.getRespuesta();
				try {
					myTypes = gson.fromJson(jObject.getString("mensaje"),
							new TypeToken<List<PuntoDeInteres>>() {
							}.getType());
					puntosDeInteres = (ArrayList<PuntoDeInteres>) myTypes;
					puntosDeInteresJArray = jObject.getString("mensaje");
					System.out.println(puntosDeInteres.get(0).getNombre());
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				llegoRespuestaServidor=true;
			}
		}while(!llegoRespuestaServidor);*/
		
			
		
		
			
		
	}
	

	public void filtrarPDIsPorCategorias(double distanciaMax,
			ArrayList<String> categorias) {
		// puntosDeInteres=puntosDeInteresDaoPDIs.buscarPDIsPorCategoria(distanciaMax,categorias);
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

	public DaoPDI getDaoPDIs() {
		return daoPDIs;
	}

	public void setDaoPDIs(DaoPDI daoPDIs) {
		this.daoPDIs = daoPDIs;
	}



	private ArrayList<PuntoDeInteres> puntosDeInteres = new ArrayList<PuntoDeInteres>();
	private String puntosDeInteresJArray = "";
	private DaoPDI daoPDIs = new DaoPDI();
	private static ControladorPDIs controlador;

}
