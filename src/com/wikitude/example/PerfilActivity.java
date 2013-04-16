package com.wikitude.example;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
		Sesion sesion = ControladorSesion.getInstance().getSesion();		
		new GestorServer().establecerDatosDePerfilEnSesion(sesion, this);										
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_perfil, menu);
		return true;
	}	
	
	public void mostrarActualizarPerfil(View view){				
		startActivity(new Intent(this, ActualizarPerfilActivity.class));
		finish();
	}	
		
}
