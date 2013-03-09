package com.wikitude.example;

import java.util.ArrayList;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class AdaptadorListPDI extends BaseAdapter {

	private static final String TAG = AdaptadorListPDI.class.getSimpleName();
	
	private ArrayList<PuntoDeInteres> listArray;
	private final ListaMisPDIActivity listaActivity;
	public AdaptadorListPDI(ArrayList<PuntoDeInteres> puntosDeInteres, ListaMisPDIActivity activity) {
		listArray = puntosDeInteres;
		listaActivity=activity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
	
	

	public ArrayList<PuntoDeInteres> getListArray() {
		return listArray;
	}

	public void setListArray(ArrayList<PuntoDeInteres> listArray) {
		this.listArray = listArray;
	}

	@Override
	public View getView(int index, View view, final ViewGroup parent) {
		// TODO Auto-generated method stub
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.single_list_item, parent, false);
		}
		final PuntoDeInteres pdi = listArray.get(index);
		TextView textview = (TextView) view.findViewById(R.id.textView3);
		textview.setText(pdi.getNombre() + "-" + pdi.getCategoria());
		ImageButton button = (ImageButton) view.findViewById(R.id.imageButton1);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listaActivity.onBorrarPDI(pdi.getId());

			}
		});
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(listaActivity, ActualizarPDIActivity.class);
				intent.putExtra("id", String.valueOf(pdi.getId()));
				listaActivity.startActivity(intent);
			}
		});

		return view;
	}

}
