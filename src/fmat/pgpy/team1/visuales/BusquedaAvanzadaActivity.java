package fmat.pgpy.team1.visuales;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import fmat.pgpy.team1.R;
import fmat.pgpy.team1.controladores.ControladorPDIs;
import fmat.pgpy.team1.dominio.Posicion;

public class BusquedaAvanzadaActivity extends Activity {
			
	private static SimpleARBrowserActivity arBrowser;			

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_busqueda_avanzada);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_busqueda_avanzada, menu);
		return true;
	}

	public void realizaBusquedaAvanzada(View view) {
		Posicion posicion = new Posicion();
		posicion.setLatitud(SimpleARBrowserActivity.getLatitudActual());
		posicion.setLongitud(SimpleARBrowserActivity.getLongitudActual());
		double rangoMax = 15;
		EditText campoBusqueda = (EditText) findViewById(R.id.campoBusqueda);
		String clave = campoBusqueda.getText().toString();
		RadioButton porNombre = (RadioButton) findViewById(R.id.radio0);
		RadioButton porDescripcion = (RadioButton) findViewById(R.id.radio1);
		RadioButton porCategoria = (RadioButton) findViewById(R.id.radioButton1);
		RadioButton porDireccion = (RadioButton) findViewById(R.id.radio2);
						
		if (porNombre.isChecked()) {
			ControladorPDIs.getInstance().filtrarPDIsPorCategorias(posicion,
					rangoMax, clave, "nombre", arBrowser);			
			System.out.println("NOMBRE");			
		}
		if (porDescripcion.isChecked()) {
			ControladorPDIs.getInstance().filtrarPDIsPorCategorias(posicion,
					rangoMax, clave, "descripcion", arBrowser);
			System.out.println("DESCRIPCION");
		}
		if (porCategoria.isChecked()) {
			ControladorPDIs.getInstance().filtrarPDIsPorCategorias(posicion,
					rangoMax, clave, "categoria", arBrowser);
			System.out.println("CATEGORIA");
		}
		if (porDireccion.isChecked()) {
			ControladorPDIs.getInstance().filtrarPDIsPorCategorias(posicion,
					rangoMax, clave, "direccion", arBrowser);
			System.out.println("DIRECCION");
		}
		
	}
	
	public static void setARBrowserBusquedaAvanzada(SimpleARBrowserActivity browserActivity){
		arBrowser = browserActivity;		
		System.out.println("setARBrowserBusquedaAvanzada");
	}
	
	
			
}
