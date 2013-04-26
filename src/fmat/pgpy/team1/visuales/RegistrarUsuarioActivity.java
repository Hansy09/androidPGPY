package fmat.pgpy.team1.visuales;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import fmat.pgpy.team1.R;
import fmat.pgpy.team1.controladores.ControladorSesion;
import fmat.pgpy.team1.dominio.PuntoDeInteres;
import fmat.pgpy.team1.dominio.Sesion;
import fmat.pgpy.team1.interfaces.RespuestaInterface;

/**
 * 
 * @Nombre SMD
 * @Fecha 01/03/2013
 * @Descripcion Clase encargada de la actividad de Registrar usuario
 *
 */
public class RegistrarUsuarioActivity extends Activity implements RespuestaInterface{

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
	
	@Override
    /**
	 * Metodo que recibe el objeto json respuesta del servidor y realiza la correspondiente accion segun la respuesta
	 */
	public void procesarRespuestaServidor(JSONObject jObject) {
		String tipoRespuesta;
		try {
			tipoRespuesta = jObject.get("codigo").toString();
		if(tipoRespuesta.equals("100")){
			Toast.makeText(this, "Usuario Registrado",Toast.LENGTH_SHORT).show();
		} else
			Toast.makeText(this, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private ControladorSesion controladorSesion = ControladorSesion.getInstance();
	private Sesion sesion;
	String correo = "";
	String contrasenia ="";
	String confirmarContrasenia="";

}
