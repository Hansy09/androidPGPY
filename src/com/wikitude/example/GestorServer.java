package com.wikitude.example;


import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * 
 * @Nombre SMD
 * @Fecha 01/03/2013
 * @Descripcion Clase encargada de conectar al server
 *
 */
public class GestorServer{

//	private String direccionBase = "http://192.168.1.67:8000/"; //Josue	
//	private String direccionBase = "http://192.168.1.83:8000/";  //Rusel
//	private String direccionBase = "http://pgpy.dyndns-ip.com:8000";
	private String direccionBase = "http://jd732.gondor.co/";

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
	public void registrarPDIEnServidor(String usuario, PuntoDeInteres pdi,RespuestaInterface activity) {

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
				new ServidorHandler(activity));

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
		rp.put("imagen", "");
		client.post(direccionBase + "/geoAdds/pdi/actualizar/", rp,
				new ServidorMensajeHandler(activity));

	}

	public void buscarPDIsPorCategoria(Posicion posicion, double rangoMaximo,
			String clave, String categoria, VisorInterface visor) {		
		
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("longitud", String.valueOf(posicion.getLongitud()));
		peticion.put("latitud", String.valueOf(posicion.getLatitud()));
		peticion.put("rangoMaximoAlcance", String.valueOf(rangoMaximo));
		peticion.put("searchString", clave);
		peticion.put("categoria", categoria);  
		httpClient.post(direccionBase + "/geoAdds/pdi/categoria/", peticion,
				new RespuestaHandler(visor));		
		
		System.out.println("buscarPDIsPorCategoria");		
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
	public void obtenerAnunciosPDI(int idPDI, ExisteFavoritoInterface act){
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("idPDI", String.valueOf(idPDI));
		Log.d("log", "mandando a handler");
		httpClient.post(direccionBase + "/geoAdds/anuncio/obtenerTodo/", peticion, new ExisteFavoritoHandler(act));
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
//		peticion.put("categoria", anuncio.getCategoria());
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
	
	/**
	 * Metodo que sirve para actualizar el nombre, el apellido, el correo, o la contraseña del usuario
	 * @param sesion La sesion del usuario que quiere cambiar alguno de sus datos
	 * @param act Activity al cual sera redirigida la respuesta de petición.
	 */
	public void actualizaUsuarioEnServidor(Sesion sesion, ActualizarPerfilActivity act){
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("idUser", String.valueOf(sesion.getId()));
		peticion.put("correo", String.valueOf( sesion.getCorreo()));
		peticion.put("contrasenia", String.valueOf( sesion.getContrasenia()));
		peticion.put("nombre", sesion.getNombre());
		peticion.put("apellido", sesion.getApellido());				
		peticion.put("URLimagen", sesion.getURLImagenDelUsuario());		
		peticion.put("edad", String.valueOf(sesion.getEdad()));		
		peticion.put("genero", sesion.getGenero());		
		httpClient.post(direccionBase + "/geoAdds/usuario/actualizar/", peticion, new ActualizaUsuarioHandler(act));
		
		System.out.println("actualizaUsuarioEnServidor: "+sesion.getURLImagenDelUsuario());
	}
	
	/**
	 * Obtiene los datos del perfil del ususario desde el servidor
	 * @param sesion La sesion de donde se obtiene el ID de sesión y el correo asociado a esta sesión.
	 * Con estos datos se realiza una consulta al servodor para obtener los datos de perfil. 
	 * @param act El activity al cual será redireccionada la respuesta del servidor.
	 */
	public void establecerDatosDePerfilEnSesion(Sesion sesion, PerfilActivity act){
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("idUser", String.valueOf(sesion.getId()));
		peticion.put("correo", String.valueOf( sesion.getCorreo()));			
		httpClient.post(direccionBase + "/geoAdds/usuario/perfil/", peticion, new PerfilUsuarioHandler(act));
	}
	
	/**
	 * Este metodo sirve para subir imagenes al servidor pero se puede usar para subir cualquier tipo de archivo. 
	 * Este metodo, en particular, esta orientado a subir imagenes para el perfil del usuario. 
	 * @param archivoImagen El archivo de imagen que se establecerá como imagen de perfil de usuario.
	 * @param act El activity al cual se redireccionará la respuesta del servidor.
	 * @throws FileNotFoundException 
	 */
	public void subirImagenDePerfilAlServidor(File archivoImagen, String correoUsuario, ActualizarPerfilActivity act) throws FileNotFoundException{		 
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();		
		peticion.put("imagen", archivoImagen);					
		peticion.put("correo", correoUsuario);
		httpClient.post(direccionBase + "/geoAdds/imagen/mostrar/", peticion, new ImagenDePerfilHandler(act));
		Toast.makeText(act, "...Subiendo imagen...",10).show();
	}	
	/**
	 * Metodo que sirve parar borrar un punto de interes en el servidor
	 * @param usuario El usuario que tiene registrado el punto de interes
	 * @param id El id del punto de interes a borrar
	 * @param activity El activity de tipo ToastInterface que manejara los mensajes de respuesta
	 */
	public void borrarPDIenServidor(String usuario, int id,RespuestaInterface activity){
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams rp = new RequestParams();
		rp.put("usuario", usuario);
		rp.put("idPDI", String.valueOf(id));
		client.post(direccionBase + "/geoAdds/pdi/eliminar/", rp,
				new ServidorHandler(activity));
	}
	
	
	public void marcarCheckBoxFavoritoEnServidor(Sesion sesion,String id,int marcado, RespuestaInterface activity){
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("correo_e", String.valueOf( sesion.getCorreo()));
		peticion.put("idPDI", String.valueOf( id));
		peticion.put("marcado", String.valueOf( marcado));
		System.out.println("Llego favorito antes server");
		httpClient.post(direccionBase + "/geoAdds/favorito/marcar/", peticion, new ServidorHandler(activity));
	}
	
	public void obtenerListaMisFavoritosEnServer(Sesion sesion, RespuestaInterface activity){
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("usuario", String.valueOf( sesion.getCorreo()));
		httpClient.post(direccionBase + "/geoAdds/usuario/listafavoritos/", peticion, new ServidorHandler(activity));
	}
	
	public void existeEnFavoritosEnServer(Sesion sesion,String id, ExisteFavoritoInterface activity){
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("usuario", String.valueOf( sesion.getCorreo()));
		peticion.put("idPDI", String.valueOf( id));
		httpClient.post(direccionBase + "/geoAdds/favorito/esfavorito/", peticion, new ExisteFavoritoHandler(activity));
	}
	
	
}
