package com.wikitude.example;



import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.SerializedName;

/**
 * Clase que representa la posicion gps de un punto de interes
 * @author Hansy
 *
 */
public class Posicion {


	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getAltitud() {
		return altitud;
	}

	public void setAltitud(double altitud) {
		this.altitud = altitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("latitud", latitud);
            obj.put("altitud", altitud);
            obj.put("longitud", longitud);
        } catch (JSONException e) {
            System.out.println("DefaultListItem.toString JSONException: "+e.getMessage());
        }
        return obj;
    }
	@SerializedName("latitud")
	private double latitud = 0;
	@SerializedName("altitud")
	private double altitud = 0;
	@SerializedName("longitud")
	private double longitud = 0;
}
