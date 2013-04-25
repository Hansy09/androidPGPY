package fmat.pgpy.team1.visuales;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import fmat.pgpy.team1.R;
import fmat.pgpy.team1.controladores.ControladorSesion;
import fmat.pgpy.team1.dominio.Sesion;

/**
 * 
 * @Nombre SMD
 * @Fecha 01/03/2013
 * @Descripcion Clase encargada de realizar la actividad de Iniciar Sesion
 *
 */
public class IniciarSesionActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iniciar_sesion);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_iniciar_sesion, menu);
		return true;
	}
	
	public void onIniciarSesion(View view){
		EditText editText = (EditText) findViewById(R.id.editText_correoInc);
		correo = editText.getText().toString();
		editText = (EditText) findViewById(R.id.editText_contInc);
		contrasenia = editText.getText().toString();
		if(controladorSesion.validarDatosCompletosInicio(correo, contrasenia)){
				sesion = new Sesion(correo, contrasenia);
				controladorSesion.iniciarSesion(sesion, this);
				this.finish();
		}
		else 
			Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
	}
	
	private ControladorSesion controladorSesion = ControladorSesion.getInstance();
	private Sesion sesion = null;
	String correo = "";
	String contrasenia ="";

}
