package fmat.pgpy.team1.visuales;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import fmat.pgpy.team1.R;
import fmat.pgpy.team1.controladores.ControladorAnuncio;
import fmat.pgpy.team1.controladores.ControladorPDIs;
import fmat.pgpy.team1.controladores.ControladorSesion;
import fmat.pgpy.team1.dominio.PuntoDeInteres;
import fmat.pgpy.team1.interfaces.ExisteFavoritoInterface;
import fmat.pgpy.team1.interfaces.RespuestaInterface;
import fmat.pgpy.team1.visuales.resource.ImageLoader;

/**
 * Activity que muestra los datos completos del punto de interes seleccionado
 * @author Hansy
 *
 */
public class PDIDetalle extends Activity implements RespuestaInterface, ExisteFavoritoInterface{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pdidetalle);
		idPdi = this.getIntent().getExtras().getString("id");
		CheckBox cBox=((CheckBox) this.findViewById(R.id.checkBox1));
        if(!contSesion.getSesionIniciada()){
        	cBox.setVisibility(View.INVISIBLE);
        }else{
        	contSesion.esFavorito(idPdi, this);
        }
		int id = Integer.parseInt(idPdi);
		ControladorPDIs controlador= ControladorPDIs.getInstance();
		ArrayList<PuntoDeInteres> puntosDeInteres=controlador.getPuntosDeInteres();
		PuntoDeInteres pdi= null;
		for(int i=0;i<puntosDeInteres.size();i++){
			if(puntosDeInteres.get(i).getId()==id){
				pdi=puntosDeInteres.get(i);
			}
		}
		if(pdi!=null){
			((TextView) this.findViewById(R.id.textView1)).setText(pdi.getNombre());
			((TextView) this.findViewById(R.id.textView3)).setText(pdi.getCategoria());
			((TextView) this.findViewById(R.id.textView5)).setText(pdi.getUrl());
			((TextView) this.findViewById(R.id.textView7)).setText(pdi.getEmail());
			((TextView) this.findViewById(R.id.textView9)).setText(pdi.getDireccion());
			((TextView) this.findViewById(R.id.textView11)).setText(pdi.getDescripcion());
			((TextView) this.findViewById(R.id.textView13)).setText(pdi.getTelefono());
			String image_url = pdi.getImagen().trim();
			if(!image_url.equals("")){
				int loader = R.drawable.loading;
				 
		        // Imageview to show
		        ImageView image = (ImageView) findViewById(R.id.imageView1);
		 
		        // Image url
		                
		        // ImageLoader class instance
		        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
		 
		        // whenever you want to load an image from url
		        // call DisplayImage function
		        // url - image url to load
		        // loader - loader image, will be displayed before getting image
		        // image - ImageView
		        imgLoader.DisplayImage(image_url, loader, image);
			}
			
	        
		}
		ControladorAnuncio contAnuncio = ControladorAnuncio.getInstance();
		contAnuncio.obtenerAnunciosDePDI(pdi.getId(), this);
		
	}
	
	public void onMarcarCheckBoxFavorito(View v){
		CheckBox cBox=((CheckBox) this.findViewById(R.id.checkBox1));
		int marcar=1;
		if(cBox.isChecked()){
			marcar=0;
		}
		
		contSesion.marcarCheckBoxFavorito(idPdi, marcar, this);
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
	
	/**
	 * Muestra los anuncios segun el PDI que se haya seleccionado previamente, 
	 * con la variable numeroPDI
	 */
	public void onVerAnuncios(){
		Intent intent = new Intent(this, ListaAnunciosActivity.class);
		intent.putExtra("idPDI", Integer.parseInt(idPdi));
		startActivity(intent);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pdidetalle, menu);
		return true;
	}
	

	@Override
	public void procesarRespuestaServidor(JSONObject jObject) {
		// TODO Auto-generated method stub
		try {
			String tipoRespuesta=jObject.get("codigo").toString();
			System.out.println("Llego respuesta de favorito");
			if(tipoRespuesta.equals("100")){
				Toast.makeText(this, jObject.get("mensaje").toString(), Toast.LENGTH_SHORT).show();
				System.out.println("el mensaje recibido: "+jObject.get("mensaje").toString());
				Gson gson = new Gson();
				PuntoDeInteres pdi=  gson.fromJson(jObject.getString("objeto"),PuntoDeInteres.class);
				boolean existeFavorito=false;
				for(int i=0;i<contSesion.getSesion().getMisFavoritos().size();i++){
					if(contSesion.getSesion().getMisFavoritos().get(i).getId()==pdi.getId()){
						existeFavorito=true;
					}
				}
				if(existeFavorito){
					contSesion.eliminarFavorito(pdi);
				}else{
					contSesion.agregarFavorito(pdi);
				}
			}else{
				Toast.makeText(this, jObject.get("mensaje").toString(), Toast.LENGTH_SHORT).show();
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Error de json");
			e.printStackTrace();
		}
	}
	@Override
	public void procesarExisteRespuestaServidor(JSONObject jObject) {
		// TODO Auto-generated method stub
		try {
			String tipoRespuesta=jObject.get("codigo").toString();
			
			if(tipoRespuesta.equals("100")){
				if(jObject.get("objeto").toString().equals("True")){
					CheckBox cBox=((CheckBox) this.findViewById(R.id.checkBox1));
					cBox.setChecked(true);
				}
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Error de json");
			e.printStackTrace();
		}
	}
	
	private ControladorSesion contSesion = ControladorSesion.getInstance();
	private String idPdi=null;
	
}
