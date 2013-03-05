package com.wikitude.example;



import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class GestorServer {

	private String direccionBase = "http://jd732.o1.gondor.io";

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

		System.out.println("Entre al gestor registro");
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams rp = new RequestParams();
		/*rp.put("usuario", "hdse09@gmail.com");
		rp.put("nombre", "park");
		rp.put("categoria", "11");
		rp.put("longitud", "-89.632213");
		rp.put("latitud", "20.958531");
		rp.put("altitud", "65.0");*/
		rp.put("usuario", usuario);
		rp.put("nombre", pdi.getNombre());
		rp.put("categoria", pdi.getCategoria());
		rp.put("longitud", String.valueOf(pdi.getPosicion().getLongitud()));
		rp.put("latitud", String.valueOf(pdi.getPosicion().getLatitud()));
		rp.put("altitud", String.valueOf(pdi.getPosicion().getAltitud()));
		System.out.println("Usuario: _"+usuario+"_");
		System.out.println("nombre: _"+String.valueOf(pdi.getNombre())+"_");
		System.out.println("categoria: _"+String.valueOf(pdi.getCategoria())+"_");
		System.out.println("longitud: _"+String.valueOf(pdi.getPosicion().getLongitud())+"_");
		System.out.println("latitud: _"+String.valueOf(pdi.getPosicion().getLatitud())+"_");
		System.out.println("altitud: _"+String.valueOf(pdi.getPosicion().getAltitud())+"_");
		System.out.println("direccion: _"+direccionBase + "/geoAdds/pdi/registrar/_");
		client.post(direccionBase + "/geoAdds/pdi/registrar/", rp,
				new ServidorMensajeHandler(activity));

	}
	
	/**
	 * Metodo que manda una solicituds de actualizacion de un punto de interes
	 * @param usuario El usuario que posee el punto de interes
	 * @param pdi El punto de interes que contiene los nuevos datos a regisrar
	 * @param activity el activity que implementa el toastinterface para manejar los mensajes del servidor
	 */
	public void actualizarPDIEnServidor(String usuario, PuntoDeInteres pdi,ToastInterface activity) {
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams rp = new RequestParams();
		rp.put("usuario", usuario);
		rp.put("idPDI", String.valueOf(pdi.getId()));
		rp.put("descripcion", pdi.getDescripcion());
		rp.put("direccion", pdi.getDireccion());
		rp.put("telefono", pdi.getTelefono());
		rp.put("paginaWeb", pdi.getUrl());
		rp.put("email", pdi.getEmail());
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
	/**
	 * Metodo que sirve parar borrar un punto de interes en el servidor
	 * @param usuario El usuario que tiene registrado el punto de interes
	 * @param id El id del punto de interes a borrar
	 * @param activity El activity de tipo ToastInterface que manejara los mensajes de respuesta
	 */
	public void borrarPDIenServidor(String usuario, int id,ToastInterface activity){
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams rp = new RequestParams();
		rp.put("usuario", usuario);
		rp.put("idPDI", String.valueOf(id));
		client.post(direccionBase + "/geoAdds/pdi/eliminar/", rp,
				new ServidorMensajeHandler(activity));
	}
}
