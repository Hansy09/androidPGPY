package com.wikitude.example;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaAnunciosActivity extends ListActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ControladorPDIs controlador = ControladorPDIs.getInstance();
		pdiLista = controlador.getPuntosDeInteres();
		Intent intent = getIntent();
		PuntoDeInteres pdi = new PuntoDeInteres();
		idPDI = intent.getIntExtra("idPDI", 0);
		for(int i=0;i<pdiLista.size();i++){
			if(pdiLista.get(i).getId()==idPDI){
				pdi=pdiLista.get(i);
			}
		}
		anuncioLista = pdi.getListaAnuncios();
		listaAnuncios = new String[anuncioLista.size()];
		for (int i = 0; i < anuncioLista.size(); i++) {
			listaAnuncios[i] = anuncioLista.get(i).getTitulo();
		}
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listaAnuncios));
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_visualizar_lista, menu);
		return true;
	}

	private int idPDI = 0;
	private ArrayList<PuntoDeInteres> pdiLista=null;
	private ArrayList<Anuncio> anuncioLista = null;
	private String listaAnuncios[] = null;
}
