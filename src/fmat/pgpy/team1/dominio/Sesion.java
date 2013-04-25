package fmat.pgpy.team1.dominio;

import java.util.ArrayList;


/**
 * 
 * @Nombre SMD
 * @Fecha 01/03/2013
 * @Descripcion Clase modelo de Sesion
 *
 */
public class Sesion {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	public String getNombre() {
		return nombreDelUsuario;
	} 
	
	public void setNombre(String nuevoNombre) {
		this.nombreDelUsuario = nuevoNombre;
	}
	
	public String getApellido() {
		return apellidoDelUsuairio;
	}
	
	public void setApellido(String nuevoApellido) { 
		this.apellidoDelUsuairio = nuevoApellido;				
	}
	
	public int getEdad(){
		return edad;
	}
	
	public void setEdad(int edad) {		
		this.edad = edad;
	}
	
	public String getGenero(){
		return genero;
	}
	
	public void setGenero(String genero){
		this.genero = genero;
	}	
		
	public String getURLImagenDelUsuario() {
		return URLImagenDelUsuario;
	}
	
	public void setURLImagen(String nuevaURLImagen) {
		this.URLImagenDelUsuario = nuevaURLImagen;
	}
	
	public ArrayList<PuntoDeInteres> getMisPDI(){
		return misPDI;
	}
	
	public void setMisPDI(ArrayList<PuntoDeInteres> PDI){
		this.misPDI = PDI;
	}	
	
	public Sesion(String correo, String contrasenia){
		setCorreo(correo);
		setContrasenia(contrasenia);
	}
	
	public ArrayList<PuntoDeInteres> getMisFavoritos() {
		return misFavoritos;
	}

	public void setMisFavoritos(ArrayList<PuntoDeInteres> misFavoritos) {
		this.misFavoritos = misFavoritos;
	}

	
	
	public Sesion(){
		
	}
	
	private int id = 0;
	private String correo = "";
	private String contrasenia = "";
	private String nombreDelUsuario = "";
	private String apellidoDelUsuairio = "";
	private int edad = 0;
	private String genero = "";
	private String URLImagenDelUsuario = "";
	private ArrayList<PuntoDeInteres> misPDI = new ArrayList<PuntoDeInteres>();	
	private ArrayList<PuntoDeInteres> misFavoritos = new ArrayList<PuntoDeInteres>();
}
