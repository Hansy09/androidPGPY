package com.wikitude.example;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @Nombre SMD
 * @Fecha 01/03/2013
 * @Descripcion Clase encargada de la actividad de Registrar usuario
 *
 */
public class RegistrarUsuarioActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrar_usuario);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_registrar_usuario, menu);
		return true;
	}
	
	public void onRegistrarUsuario(View view){
		EditText editText = (EditText) findViewById(R.id.editText_correoReg);
		correo = editText.getText().toString();
		editText = (EditText) findViewById(R.id.editText_contReg);
		contrasenia = editText.getText().toString();
		editText = (EditText) findViewById(R.id.editText_confcontReg);
		confirmarContrasenia = editText.getText().toString();
		if(controladorSesion.validarDatosCompletosRegistro(correo, contrasenia, confirmarContrasenia)){
			if(controladorSesion.confirmarContrasenia(contrasenia, confirmarContrasenia)){
				sesion = new Sesion(correo, contrasenia);
				controladorSesion.registrarUsuario(sesion, this);
			} else
				Toast.makeText(this, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
		
		}else 
			Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
	}
	

	private ControladorSesion controladorSesion = ControladorSesion.getInstance();
	private Sesion sesion;
	String correo = "";
	String contrasenia ="";
	String confirmarContrasenia="";

}
