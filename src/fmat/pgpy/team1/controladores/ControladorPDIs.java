package fmat.pgpy.team1.controladores;

import java.util.ArrayList;

import org.json.JSONArray;

import fmat.pgpy.team1.dominio.Posicion;
import fmat.pgpy.team1.dominio.PuntoDeInteres;
import fmat.pgpy.team1.interfaces.RespuestaInterface;
import fmat.pgpy.team1.interfaces.ToastInterface;
import fmat.pgpy.team1.operadores.GestorServer;

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
			RespuestaInterface visor) {
		daoPDIs.buscarDentroDeRangoMax(posicion, distanciaMax, visor);
	}
	/**
	 * Metodo que sirve para registrar un nuevo punto de interes
	 * @param pdi El punto de interes a registrar
	 * @param activity El acitivity que mostrara los mensajes del servidor
	 */
	public void registrarPDI(String usuario, PuntoDeInteres pdi,RespuestaInterface activity) {
		System.out.println("Entre al contr regis");
		daoPDIs.registrarPDIEnServidor(usuario, pdi,activity);
	}
	
	/**
	 * Metodo que sirve para actualizar un punto de interes
	 * @param usuario El usuario que tiene registrado el punto de interes
	 * @param pdi El punto de interes a actualizar
	 * @param activity El activity tipo ToastInterface que maneja los mensajes del servidor
	 */
	public void actualizarPDI(String usuario, PuntoDeInteres pdi,RespuestaInterface activity) {
		
		daoPDIs.actualizarPDIEnServidor(usuario, pdi,activity);
	}
	/**
	 * Metodo que sirve para borrar un punto de interes
	 * @param usuario el usuario que tiene el punto de interes a borrar
	 * @param id El id del punto de interes a borrar
	 * @param activity El activity de tipo ToastInterface que maneja los mensajes del servidor
	 */
    public void borrarPDI(String usuario, int id,RespuestaInterface activity) {
		
		daoPDIs.borrarPDIenServidor(usuario, id,activity);
	}

	public void filtrarPDIsPorCategorias(Posicion posicion,
			double distanciaMax, String clave, String categoria,
			RespuestaInterface visor) {
		esBusquedaAvanzada = true;
		daoPDIs.buscarPDIsPorCategoria(posicion, distanciaMax, clave,
				categoria, visor);
	}

	public void filtrarPDIsPorNombre(Posicion posicion, double distanciaMax,
			String clave, RespuestaInterface visor) {
		esBusquedaSimple = true;
		daoPDIs.buscarPDIsPorCategoria(posicion, distanciaMax, clave,
				"nombre", visor);
	}

	
	public void actualizarJSONArrayPDIs(){
		JSONArray nuevoJSONArray = new JSONArray();
		for (int i=0;i<puntosDeInteres.size();i++) {
		    nuevoJSONArray.put(puntosDeInteres.get(i).getJSONObject());
		}
		this.setPuntosDeInteresJArray(nuevoJSONArray.toString());
	}
	
	public void borrarPDIDeArray(int id){
		for (int i=0;i<puntosDeInteres.size();i++) {
		    if(puntosDeInteres.get(i).getId()==id){
		    	puntosDeInteres.remove(i);
		    	break;
		    }
		}
		this.actualizarJSONArrayPDIs();
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

	public boolean esUnaBusquedaAvanzada() {
		return esBusquedaAvanzada;
	}

	public boolean esUnaBusquedaSimple() {
		return esBusquedaSimple;
	}
	
	
	
	public double getDistanciaSeleccionada() {
		return distanciaSeleccionada;
	}

	public void setDistanciaSeleccionada(double distanciaSeleccionada) {
		this.distanciaSeleccionada = distanciaSeleccionada;
	}



	private ArrayList<PuntoDeInteres> puntosDeInteres = new ArrayList<PuntoDeInteres>();
	private String puntosDeInteresJArray = "";
	private GestorServer daoPDIs = new GestorServer();
	private static ControladorPDIs controlador;
	private  double longitudActual = 13.069730f;
	private  double latitudActual = 47.77318f;
	private double altitudActual=0;
	private double distanciaSeleccionada=7.5;

	private static boolean esBusquedaAvanzada = false;
	private static boolean esBusquedaSimple = false;

}
