package com.wikitude.example;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Activity que muestra los datos completos del punto de interes seleccionado
 * @author Hansy
 *
 */
public class PDIDetalle extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pdidetalle);
		String idS = this.getIntent().getExtras().getString("id");
		int id = Integer.parseInt(idS);
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
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pdidetalle, menu);
		return true;
	}

}
