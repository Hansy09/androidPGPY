package com.wikitude.example;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;


/**
 * 
 * @Nombre SMD
 * @Fecha 01/03/2013
 * @Descripcion Clase encargada de conectar al server
 *
 */
public class GestorServer{

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
	
	public void verificarInicioSesionEnServidor(Sesion sesion, IniciarSesionActivity act){
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("correo", String.valueOf( sesion.getCorreo()));
		peticion.put("contrasenia", String.valueOf( sesion.getContrasenia()));
		httpClient.post(direccionBase + "/geoAdds/usuario/iniciarSesion/", peticion, new InicioSesionHandler(act));
	}
	
	public void registrarUsuarioEnServidor(Sesion sesion, RegistrarUsuarioActivity act){
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("correo", String.valueOf( sesion.getCorreo()));
		peticion.put("contrasenia", String.valueOf( sesion.getContrasenia()));
		httpClient.post(direccionBase + "/geoAdds/usuario/registrar/", peticion, new RegistroUsuarioHandler(act));
	}

	public void registrarAnuncioEnServidor(Anuncio anuncio, int idPDI,RespuestaInterface act) {
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		ControladorSesion contSesion = ControladorSesion.getInstance();
		String correo = contSesion.getSesion().getCorreo();
		peticion.put("idPDI", String.valueOf(idPDI));
		peticion.put("correo_e", correo);
		peticion.put("titulo", anuncio.getTitulo());
		peticion.put("descripcion", anuncio.getDescripcion());
		peticion.put("categoria", anuncio.getCategoria());
		peticion.put("URLimagen", "");
		Log.d("log", "mandando a handler");
		httpClient.post(direccionBase + "/geoAdds/anuncio/registrar/", peticion, new ServidorHandler(act));
	}
	
	/**
	 * Esta clase se utiliza para cualquier lugar donde se necesiten obtener los Anuncios de algun PDI
	 * ya registrado previamente
	 * @param idPDI
	 * @param act
	 */
	public void obtenerAnunciosPDI(int idPDI, RespuestaInterface act){
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("idPDI", String.valueOf(idPDI));
		Log.d("log", "mandando a handler");
		httpClient.post(direccionBase + "/geoAdds/anuncio/obtenerTodo/", peticion, new ServidorHandler(act));
	}

	public void actualizarAnuncioEnServidor(Anuncio anuncio, int idPDI,RespuestaInterface act) {
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		ControladorSesion contSesion = ControladorSesion.getInstance();
		String correo = contSesion.getSesion().getCorreo();
		peticion.put("idAnuncio", String.valueOf(anuncio.getId()));
		peticion.put("idPDI", String.valueOf(idPDI));
		peticion.put("correo_e", correo);
		peticion.put("titulo", anuncio.getTitulo());
		peticion.put("descripcion", anuncio.getDescripcion());
		peticion.put("categoria", anuncio.getCategoria());
		peticion.put("URLimagen", anuncio.getImagen());
		httpClient.post(direccionBase + "/geoAdds/anuncio/modificar/", peticion, new ServidorHandler(act));
	}

	public void eliminarAnuncioEnServidor(int idAnuncio, int idPDI, RespuestaInterface act) {
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		ControladorSesion contSesion = ControladorSesion.getInstance();
		String correo = contSesion.getSesion().getCorreo();
		peticion.put("idAnuncio", String.valueOf(idAnuncio));
		peticion.put("idPDI", String.valueOf(idPDI));
		peticion.put("correo_e", correo);
		httpClient.post(direccionBase + "/geoAdds/anuncio/eliminar/", peticion, new ServidorHandler(act));
	}
	
}
