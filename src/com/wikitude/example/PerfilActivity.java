package com.wikitude.example;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 
 * @Nombre SMD
 * @Fecha 01/03/2013
 * @Descripcion Clase encargada de la actividad del perfil
 *
 */
public class PerfilActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_perfil, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		switch (itemId) {
		case R.id.menu_Reg:
			this.visualizarRegistro();
			break;
		case R.id.menu_milista:
			this.visualizarMiLista();
			break;
		case R.id.menu_Favo:
			this.visualizarMisFavoritos();
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	

	private void visualizarMiLista() {
		Intent intent = new Intent(this, ListaMisPDIActivity.class);
		// intent.putStringArrayListExtra(LISTA_PDI, poiBeanList);
		startActivity(intent);
	}
	private void visualizarMisFavoritos() {
		Intent intent = new Intent(this, ListaMisFavoritosActivity.class);
		// intent.putStringArrayListExtra(LISTA_PDI, poiBeanList);
		startActivity(intent);
	}
	
	
	private void visualizarRegistro() {
		Intent intent = new Intent(this, RegistroPDIActivity.class);
		// intent.putStringArrayListExtra(LISTA_PDI, poiBeanList);
		startActivity(intent);

	}

}
