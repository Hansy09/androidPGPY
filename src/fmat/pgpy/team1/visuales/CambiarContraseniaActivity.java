package fmat.pgpy.team1.visuales;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import fmat.pgpy.team1.R;
import fmat.pgpy.team1.controladores.ControladorSesion;
import fmat.pgpy.team1.dominio.Sesion;

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
		String contrasenaActual = ((EditText) findViewById(R.id.txtFld_contraseniaActual)).getText().toString();
		String contrasenaNueva = ((EditText) findViewById(R.id.txtFld_contraseniaNueva)).getText().toString();
		String contrasenaNuevaConfirmacion = ((EditText) findViewById(R.id.txtFld_confirmaContrasenia)).getText().toString();		
		if (!contrasenaActual.equals("") && !contrasenaNueva.equals("") && !contrasenaNuevaConfirmacion.equals("")) {
			if (sesion.getContrasenia().equals(contrasenaActual)) {
				System.out.println("________________LA CONTRASEÑA ACTUAL ES CORRECTA");
				if (contrasenaNueva.equals(contrasenaNuevaConfirmacion)) {
					sesion.setContrasenia(contrasenaNueva);					
					Toast.makeText(this, "Nueva contraseña establecida",Toast.LENGTH_SHORT).show();					
					startActivity(new Intent(this,ActualizarPerfilActivity.class));					
					finish();
				} else {
					Toast.makeText(this,"Los campos 'Escriba su nueva contraseña' y 'Confirme su nueva contraseña' no coinciden",Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(this, "La contraseña actual no es correcta",	Toast.LENGTH_SHORT).show();
			}
		}
		else {
			Toast.makeText(this, "Por favor llene todos los campos",Toast.LENGTH_SHORT).show();
		}
	}

}
