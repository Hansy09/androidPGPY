package fmat.pgpy.team1.visuales;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fmat.pgpy.team1.R;
import fmat.pgpy.team1.controladores.ControladorAnuncio;
import fmat.pgpy.team1.controladores.ControladorPDIs;
import fmat.pgpy.team1.dominio.Anuncio;
import fmat.pgpy.team1.dominio.PuntoDeInteres;
import fmat.pgpy.team1.interfaces.RespuestaAlternativaInterface;

public class ListaAnunciosActivity extends ListActivity implements RespuestaAlternativaInterface{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		idPDI = intent.getIntExtra("idPDI", 0);
		ControladorAnuncio contAnuncio = ControladorAnuncio.getInstance();
		contAnuncio.obtenerAnunciosDePDI(idPDI, this);
		
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position,
			long id) {
		super.onListItemClick(list, view, position, id);
		Anuncio anuncio = anuncioLista.get(position);
		Intent intent = new Intent(this, AnuncioActivity.class);
		intent.putExtra("titulo", anuncio.getTitulo());
		intent.putExtra("idPDI", idPDI);
		this.startActivity(intent);
		
	}

	@Override
	public void procesarRespuestaAlternativaServidor(JSONObject jObject) {
		String tipoRespuesta;
		try {
			tipoRespuesta = jObject.get("codigo").toString();
		if(tipoRespuesta.equals("100")){
			Gson gson = new Gson();
			List<Anuncio> myTypes = null;
			myTypes = gson.fromJson(jObject.getString("objeto"),
					new TypeToken<List<Anuncio>>() {
					}.getType());
			ArrayList<Anuncio> anuncios = (ArrayList<Anuncio>) myTypes;
			ControladorPDIs controlador = ControladorPDIs.getInstance();
			pdiLista = controlador.getPuntosDeInteres();
			for(int i=0;i<pdiLista.size();i++){
				if(pdiLista.get(i).getId()==idPDI){
					pdi=pdiLista.get(i);
					controlador.getPuntosDeInteres().get(i).setListaAnuncios(anuncios);
				}
			}
			pdi.setListaAnuncios(anuncios);
			anuncioLista = pdi.getListaAnuncios();
			listaAnuncios = new String[anuncioLista.size()];
			for (int i = 0; i < anuncioLista.size(); i++) {
				listaAnuncios[i] = anuncioLista.get(i).getTitulo();
			}
			setListAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, listaAnuncios));
			Toast.makeText(this, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
		}
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_visualizar_lista, menu);
		return true;
	}
	
	@Override
	public void mostrarMensaje(String mensaje) {
		Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
		
	}

	private int idPDI = 0;
	private PuntoDeInteres pdi = null;
	private ArrayList<PuntoDeInteres> pdiLista=null;
	private ArrayList<Anuncio> anuncioLista = null;
	private String listaAnuncios[] = null;
}
