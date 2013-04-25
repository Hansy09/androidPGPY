package fmat.pgpy.team1.dominio;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.SerializedName;

/**
 * Clase que representa un anuncio
 * @author Hansy
 *
 */
public class Anuncio {

	
	public int getIdPDI() {
		return idPDI;
	}

	public void setIdPDI(int idPDI) {
		this.idPDI = idPDI;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Anuncio(){
		
	}
	
	public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id_anuncio", id);
            obj.put("id_pdi", idPDI);
            obj.put("anuncio", titulo);
            obj.put("descripcion", descripcion);
            obj.put("categoria", categoria);
            obj.put("imagen", imagen);
        } catch (JSONException e) {
            System.out.println("DefaultListItem.toString JSONException: "+e.getMessage());
        }
        return obj;
    }
	
	@SerializedName("id_pdi")
	private int idPDI = 0;
	@SerializedName("id_anuncio")
	private int id = 0;
	@SerializedName("descripcion")
	private String descripcion = "";
	@SerializedName("anuncio")
	private String titulo = "";
	@SerializedName("categoria")
	private String categoria = "";
	@SerializedName("imagen")
	private String imagen = "";
}
