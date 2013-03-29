package com.wikitude.example;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Activity que muestra los datos completos del punto de interes seleccionado
 * @author Hansy
 *
 */
public class PDIDetalle extends Activity implements RespuestaInterface {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pdidetalle);
		String idS = this.getIntent().getExtras().getString("id");
		int id = Integer.parseInt(idS);
		ControladorPDIs controlador= ControladorPDIs.getInstance();
		ArrayList<PuntoDeInteres> puntosDeInteres=controlador.getPuntosDeInteres();
		for(int i=0;i<puntosDeInteres.size();i++){
			if(puntosDeInteres.get(i).getId()==id){
				pdi=puntosDeInteres.get(i);
			}
		}
		if(pdi!=null){
			((TextView) this.findViewById(R.id.textView1)).setText(pdi.getNombre());
			((TextView) this.findViewById(R.id.textView3)).setText(pdi.getCategoria());
			((TextView) this.findViewById(R.id.textView5)).setText(pdi.getDescripcion());
			((TextView) this.findViewById(R.id.textView7)).setText(pdi.getDireccion());
			((TextView) this.findViewById(R.id.textView9)).setText(pdi.getTelefono());
			((TextView) this.findViewById(R.id.textView11)).setText(pdi.getUrl());
			((TextView) this.findViewById(R.id.textView13)).setText(pdi.getEmail());
			int loader = R.drawable.loading;
			 
	        // Imageview to show
	        ImageView image = (ImageView) findViewById(R.id.imageView1);
	 
	        // Image url
	        String image_url = "http://arquidiocesiscali.org/apc-aa-files/38373837383738733837383773383738/aguaparque.JPG";
	 
	        // ImageLoader class instance
	        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
	 
	        // whenever you want to load an image from url
	        // call DisplayImage function
	        // url - image url to load
	        // loader - loader image, will be displayed before getting image
	        // image - ImageView
	        imgLoader.DisplayImage(image_url, loader, image);
		}
		ControladorAnuncio contAnuncio = ControladorAnuncio.getInstance();
		contAnuncio.obtenerAnunciosDePDI(pdi.getId(), this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pdidetalle, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		switch (itemId) {
		case R.id.menu_verAnuncios:
			this.onVerAnuncios();
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}
	
	public void onVerAnuncios(){
		Log.d("log", "entrar a ver anuncios");
		Intent intent = new Intent(this, ListaAnunciosActivity.class);
		intent.putExtra("idPDI", pdi.getId());
		startActivity(intent);
	}
	
	@Override
	public void procesarRespuestaServidor(JSONObject jObject) {
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
			pdi.setListaAnuncios(anuncios);
			Log.d("log", "100 alcanzado");
			Toast.makeText(this, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
		} else {
			Log.d("log", "100 no alcanzado");
			Toast.makeText(this, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
		}
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	PuntoDeInteres pdi= null;

}
