package com.wikitude.example;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class AnuncioActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_anuncio);
		Intent intent = getIntent();
		
		if(intent.getBooleanExtra("notificacion", false)){
			((TextView) this.findViewById(R.id.textView1)).setText(intent.getStringExtra("titulo"));
			((TextView) this.findViewById(R.id.textView3)).setText(intent.getStringExtra("categoria"));
			((TextView) this.findViewById(R.id.textView5)).setText(intent.getStringExtra("descripcion"));
		} else {
		String titulo = intent.getStringExtra("titulo");
		int id = intent.getIntExtra("idPDI", 0);
		ArrayList<PuntoDeInteres> puntosDeInteres=controlador.getPuntosDeInteres();
		PuntoDeInteres pdi= null;
		for(int i=0;i<puntosDeInteres.size();i++){
			if(puntosDeInteres.get(i).getId()==id){
				pdi=puntosDeInteres.get(i);
			}
		}
		Anuncio anuncio = new Anuncio();
		ArrayList<Anuncio> anunciosPDI = pdi.getListaAnuncios();
		for (int i = 0; i < anunciosPDI.size(); i++) {
			if (anunciosPDI.get(i).getTitulo().equals(titulo)) {
				anuncio = anunciosPDI.get(i);
			}
		}
		

		((TextView) this.findViewById(R.id.textView1)).setText(anuncio.getTitulo());
		((TextView) this.findViewById(R.id.textView3)).setText(anuncio.getCategoria());
		((TextView) this.findViewById(R.id.textView5)).setText(anuncio.getDescripcion());
		int loader = R.drawable.loading;
		 
        // Imageview to show
        ImageView image = (ImageView) findViewById(R.id.imageView1);
 
        // Image url
        String image_url = anuncio.getImagen();
 
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
	
	ControladorPDIs controlador = ControladorPDIs.getInstance();
}
