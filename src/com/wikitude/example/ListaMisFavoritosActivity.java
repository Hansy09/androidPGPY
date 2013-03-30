package com.wikitude.example;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaMisFavoritosActivity extends Activity implements RespuestaInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mis_pdi);
        mainListView = (ListView) findViewById( R.id.listView1 );
        contSesion.obtenerListaMisFavoritos(this);
        
        

    }
    
    
    
    public void llamarDetalle(PuntoDeInteres pdi){
    	Intent intent = new Intent(this, PDIDetalle.class);
		intent.putExtra("id", String.valueOf(pdi.getId()));
		this.startActivity(intent);
    	
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_lista_mis_pdi, menu);
        return true;
    }
    
    
    @Override
    /**
	 * Metodo que recibe el objeto json respuesta del servidor y realiza la correspondiente accion segun la respuesta
	 */
	public void procesarRespuestaServidor(JSONObject jObject) {
		
		try {
			String tipoRespuesta=jObject.get("codigo").toString();
			if(tipoRespuesta.equals("100")){
				Toast.makeText(this, jObject.get("mensaje").toString(), Toast.LENGTH_SHORT).show();
				ArrayList<PuntoDeInteres> misFavoritos=contSesion.getSesion().getMisFavoritos();
				Gson gson = new Gson();
				List<PuntoDeInteres> myTypes = null;
				myTypes = gson.fromJson(jObject.getString("objeto"),
						new TypeToken<List<PuntoDeInteres>>() {
						}.getType());
				ArrayList<PuntoDeInteres> puntosDeInteres = (ArrayList<PuntoDeInteres>) myTypes;
				contSesion.getSesion().setMisFavoritos(puntosDeInteres);
				values = contSesion.getSesion().getMisFavoritos();
		        customAdapter = new ArrayAdapter<PuntoDeInteres>(this,
						android.R.layout.simple_list_item_1, values);

				mainListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		        mainListView.setAdapter(customAdapter);
				
			}else{
				Toast.makeText(this, jObject.get("mensaje").toString(), Toast.LENGTH_SHORT).show();
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Error con el json de eliminar de server");
			e.printStackTrace();
		}
	}
    
   
    
    private ArrayAdapter<PuntoDeInteres>  customAdapter=null;
	private ControladorSesion contSesion = ControladorSesion.getInstance();
	private List<PuntoDeInteres> values =null;
	private ListView mainListView =null;
	
    
}
