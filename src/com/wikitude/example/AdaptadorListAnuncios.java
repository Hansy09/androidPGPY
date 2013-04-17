package com.wikitude.example;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * 
 * @Autor SMD
 * @Fecha 29/03/2013
 * @Descripcion Clase encargada de adaptar la lista de Anuncios para
 * obtener las funcionalidades deseadas
 *
 */
public class AdaptadorListAnuncios extends BaseAdapter{
	
	private ArrayList<Anuncio> listArray = null;
	private int idPDI = 0;
	private final ListaMisAnunciosPDIActivity listaActivity;
	
	public AdaptadorListAnuncios(ArrayList<Anuncio> listaAnuncios, int idPDI, ListaMisAnunciosPDIActivity activity) {
		this.listArray = listaAnuncios;
		this.idPDI = idPDI;
		this.listaActivity=activity;
	}

	@Override
	public int getCount() {
		return listArray.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listArray.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	public ArrayList<Anuncio> getListArray() {
		return listArray;
	}

	public void setListArray(ArrayList<Anuncio> listArray) {
		this.listArray = listArray;
	}

	/**
	 * Se obtiene la vista actual y se actualiza la lista de donde proviene el adaptador
	 */
	@Override
	public View getView(int index, View view, ViewGroup parent) {
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.single_list_item_anuncio, parent, false);
		}
		Log.d("log","prueba");
		final Anuncio anuncio = listArray.get(index);
		Log.d("log", anuncio.getTitulo());
		TextView textview = (TextView) view.findViewById(R.id.textView1);
		textview.setText(anuncio.getTitulo());
		ImageButton button = (ImageButton) view.findViewById(R.id.imageButton1);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(v.getContext())
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Borrando")
				.setMessage("Estas seguro de borrarlo?")
				.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						listaActivity.onBorrarAnuncio(anuncio, anuncio.getId());

					}

				}).setNegativeButton("No", null).show();

			}
		});
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(listaActivity, ActualizarAnuncioActivity.class);
				intent.putExtra("idAnuncio", anuncio.getId());
				intent.putExtra("idPDI", idPDI);
				listaActivity.startActivity(intent);
			}
		});

		return view;
	}

}
