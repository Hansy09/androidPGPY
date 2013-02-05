package com.wikitude.example;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class PuntoDeInteres {

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Posicion getPosicion() {
		return posicion;
	}

	public void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	public ArrayList<Anuncio> getListaAnuncios() {
		return listaAnuncios;
	}

	public void setListaAnuncios(ArrayList<Anuncio> anuncios) {
		this.listaAnuncios = anuncios;
	}

	@SerializedName("id")
	public int id = 0;
	@SerializedName("nombre")
	private String nombre = "";
	@SerializedName("categoria")
	private String categoria = "";
	@SerializedName("direccion")
	private String direccion = "";
	@SerializedName("telefono")
	private String telefono = "";
	@SerializedName("url")
	private String url = "";
	@SerializedName("correoElectronico")
	private String email = "";
	@SerializedName("urlImagen")
	private String imagen = "";
	@SerializedName("descripcion")
	private String descripcion = "";
	@SerializedName("posicion")
	private Posicion posicion = new Posicion();
	private ArrayList<Anuncio> listaAnuncios = new ArrayList<Anuncio>();

}
