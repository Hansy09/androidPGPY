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
		
		TextView elNombreDelUsuario = (TextView) findViewById(R.id.textView2);
		TextView elApellidoDelUsuario = (TextView) findViewById(R.id.textView4);
		TextView elCorreoDelUsuario = (TextView) findViewById(R.id.textView6);
		ImageView imagenDePerfil = (ImageView) findViewById(R.id.imageView1);
		
		Sesion sesion = ControladorSesion.getInstance().getSesion();
		
//		Falta la URL de la imagen del usuario
		elNombreDelUsuario.setText(sesion.getNombre());
		elApellidoDelUsuario.setText(sesion.getApellido());
		elCorreoDelUsuario.setText(sesion.getCorreo());		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_perfil, menu);
		return true;
	}
	
	public void mostrarActualizarPerfil(View view){
		Intent intent = new Intent(this, ActualizarPerfilActivity.class);
		startActivity(intent);
	}

}
