package com.wikitude.example;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class CambiarContraseniaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cambiar_contrasenia);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cambiar_contrasenia, menu);
		return true;
	}
	
	public void actualizarNuevaContrasena(View view){
		Sesion sesion = ControladorSesion.getInstance().getSesion();
		String contrasenaActual = findViewById(R.id.txtFld_contraseniaActual).toString();
		String contrasenaNueva = findViewById(R.id.txtFld_contraseniaNueva).toString();
		String contrasenaNuevaConfirmacion = findViewById(R.id.txtFld_confirmaContrasenia).toString();		
//		La contrase�a esta encriptada, por tanto no es posible compararlas con un simple equals.
		if(sesion.getContrasenia().equals(contrasenaActual)){
			System.out.println("________________LA CONTRASE�A ACTUAL ES CORRECTA");
			if(contrasenaNueva.equals(contrasenaNuevaConfirmacion)){
				sesion.setContrasenia(contrasenaNueva);
				startActivity(new Intent(this, ActualizarPerfilActivity.class));
				Toast.makeText(this, "Nueva contrase�a establecida",Toast.LENGTH_SHORT).show();
			} 
			else {
				Toast.makeText(this, "Los campos 'Escriba su nueva contrase�a' y 'Confirme su nueva contrase�a' no coinciden",Toast.LENGTH_LONG).show();
			}
		}	
		else {
			Toast.makeText(this, "La contrase�a actual no es correcta. [Existe un problema de comparaci�n, REVISAR] ",Toast.LENGTH_LONG).show();
		}
	}

}
