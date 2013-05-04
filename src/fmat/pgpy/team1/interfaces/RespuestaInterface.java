package fmat.pgpy.team1.interfaces;

import org.json.JSONObject;

public interface RespuestaInterface {

	public void procesarRespuestaServidor(JSONObject jObject);
	public void mostrarMensaje(String mensaje);
}
