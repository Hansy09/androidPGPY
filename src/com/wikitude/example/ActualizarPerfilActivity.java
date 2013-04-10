package com.wikitude.example;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class ActualizarPerfilActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualizar_perfil);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_actualizar_perfil, menu);
		return true;
	}
	
	public void cambiarContrasenia(View view){		
		startActivity(new Intent(this, CambiarContraseniaActivity.class));
	}
	
	public void actualizarPerfil(View view){
		establecerDatosDeSesion();		
//		new GestorServer().actualizaUsuarioEnServidor(ControladorSesion.getInstance().getSesion(), this);		
		Toast.makeText(this, "...Actualizando perfil de usuario...",Toast.LENGTH_SHORT).show();
		startActivity(new Intent(this, PerfilActivity.class));
	}		
	
	private void establecerDatosDeSesion(){		
		Sesion sesion = ControladorSesion.getInstance().getSesion();
		
		String nuevoNombre = findViewById(R.id.txtFld_nombreUsuario).toString();
		sesion.setNombre(nuevoNombre);
		
		String nuevoApellido = findViewById(R.id.txtFld_apellidoUsuario).toString();
		sesion.setApellido(nuevoApellido);
		
		String nuevoCorreo = findViewById(R.id.txtFld_correoUsuario).toString();
		sesion.setCorreo(nuevoCorreo);
		
		String nuevaContrasenia = findViewById(R.id.txtFld_contraseniaNueva).toString();
		sesion.setContrasenia(nuevaContrasenia);

//		String urlImagen = findViewById(id);
		ControladorSesion.getInstance().getSesion().setURLImagen(""); //<-- Temporal, solo para probar.
	}

}
