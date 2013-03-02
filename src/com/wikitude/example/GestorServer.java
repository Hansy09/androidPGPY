package com.wikitude.example;



import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class GestorServer {

	private String direccionBase = "http://jd732.o1.gondor.io/";

	public void buscarDentroDeRangoMax(Posicion posicion, double rangoMaximo,
			VisorInterface visor) {

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams rp = new RequestParams();

		rp.put("longitud", String.valueOf(posicion.getLongitud()));
		rp.put("latitud", String.valueOf(posicion.getLatitud()));
		rp.put("rangoMaximoAlcance", String.valueOf(rangoMaximo));
		client.post(direccionBase + "/geoAdds/pdi/lista/", rp,
				new RespuestaHandler(visor));

	}
	/**
	 * Metodo que se utiliza para registrar en el servidor el punto de interes seleccionado y maneja los mensaje tirados
	 * por el servidor
	 * @param pdi Punto de interes a registrar
	 * @param activity Activity que implementa la interfaz ToastInterface que mostrar los mensajes recibidos
	 */
	public void registrarPDIEnServidor(String usuario, PuntoDeInteres pdi,ToastInterface activity) {

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams rp = new RequestParams();
		rp.put("usuario", usuario);
		rp.put("nombre", String.valueOf(pdi.getNombre()));
		rp.put("categoria", String.valueOf(pdi.getCategoria()));
		rp.put("longitud", String.valueOf(pdi.getPosicion().getLongitud()));
		rp.put("latitud", String.valueOf(pdi.getPosicion().getLatitud()));
		rp.put("altitud", String.valueOf(pdi.getPosicion().getAltitud()));
		client.post(direccionBase + "/geoAdds/pdi/registrar/", rp,
				new ServidorMensajeHandler(activity));

	}
	
	
	public void actualizarPDIEnServidor(String usuario, PuntoDeInteres pdi,ToastInterface activity) {
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams rp = new RequestParams();
		rp.put("usuario", usuario);
		rp.put("id", String.valueOf(pdi.getId()));
		rp.put("descripcion", String.valueOf(pdi.getDescripcion()));
		rp.put("direccion", String.valueOf(pdi.getDireccion()));
		rp.put("telefono", String.valueOf(pdi.getTelefono()));
		rp.put("url", String.valueOf(pdi.getUrl()));
		rp.put("email", String.valueOf(pdi.getEmail()));
		client.post(direccionBase + "/geoAdds/pdi/actualizar/", rp,
				new ServidorMensajeHandler(activity));

	}

	public void buscarPDIsPorCategoria(Posicion posicion, double rangoMaximo,
			String clave, String categoria, VisorInterface visor) {

		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("longitud", String.valueOf(posicion.getLongitud()));
		peticion.put("latitud", String.valueOf(posicion.getLatitud()));
		peticion.put("clave", clave);
		peticion.put("categoria", categoria);
		peticion.put("rangoMaximoAlcance", String.valueOf(rangoMaximo));
		httpClient.post(direccionBase + "/geoAdds/pdi/lista/", peticion,
				new RespuestaHandler(visor));

	}
}
