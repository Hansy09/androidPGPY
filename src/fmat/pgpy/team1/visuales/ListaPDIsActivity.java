package fmat.pgpy.team1.visuales;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fmat.pgpy.team1.R;
import fmat.pgpy.team1.controladores.ControladorPDIs;
import fmat.pgpy.team1.dominio.PuntoDeInteres;

/**
 * Nombre: Santiago Martin 
 * Fecha: 5 Febrero 2013 
 * Descripcion: Actividad para
 * mostrar los PDI en forma de Lista
 */
public class ListaPDIsActivity extends ListActivity {

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		System.out.println("Entro al ListaPDIsActivity");
		ControladorPDIs controlador = ControladorPDIs.getInstance();
		ArrayList<PuntoDeInteres> pdisTotales=new ArrayList<PuntoDeInteres>();
		System.out.println("La distancia permitida es de "+controlador.getDistanciaSeleccionada());
		for(int i=0;i<controlador.getPuntosDeInteres().size();i++){
			//System.out.println("La distancia entre el pdi "+controlador.getPuntosDeInteres().get(i).getNombre()+" y mi posicion es de "+pdisTotales.get(i).getDistancia());
			if(controlador.getPuntosDeInteres().get(i).getDistancia()<=controlador.getDistanciaSeleccionada()){
				pdisTotales.add(controlador.getPuntosDeInteres().get(i));
			}
				
		}
		System.out.println("El numero de pdis permitidos es de "+pdisTotales.size());
		pdiLista = pdisTotales;
		setListAdapter(new ArrayAdapter<PuntoDeInteres>(this,
				android.R.layout.simple_list_item_1, pdiLista));
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position,
			long id) {
		super.onListItemClick(list, view, position, id);
		PuntoDeInteres pdi = pdiLista.get(position);
		Intent intent = new Intent(this, PDIDetalle.class);
		intent.putExtra("id", String.valueOf(pdi.getId()));
		this.startActivity(intent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_visualizar_lista, menu);
		return true;
	}

	private ArrayList<PuntoDeInteres> pdiLista=null;
}
