package fmat.pgpy.team1.visuales;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fmat.pgpy.team1.R;
import fmat.pgpy.team1.controladores.ControladorSesion;
import fmat.pgpy.team1.dominio.PuntoDeInteres;
import fmat.pgpy.team1.dominio.Sesion;
import fmat.pgpy.team1.interfaces.RespuestaInterface;

/**
 * 
 * @Nombre SMD
 * @Fecha 01/03/2013
 * @Descripcion Clase encargada de realizar la actividad de Iniciar Sesion
 *
 */
public class IniciarSesionActivity extends Activity implements RespuestaInterface{


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
				
		}
		else 
			Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
	}
	
	 @Override
	    /**
		 * Metodo que recibe el objeto json respuesta del servidor y realiza la correspondiente accion segun la respuesta
		 */
		public void procesarRespuestaServidor(JSONObject jObject) {
		 ControladorSesion contSesion = ControladorSesion.getInstance();
	    	String tipoRespuesta;
			try {
				tipoRespuesta = jObject.get("codigo").toString();
			if(tipoRespuesta.equals("100")){
				contSesion.setSesionIniciada(true);
				Gson gson = new Gson();
				ControladorSesion controlador = ControladorSesion.getInstance();
				List<PuntoDeInteres> myTypes = null;
				myTypes = gson.fromJson(jObject.getString("objeto"),
						new TypeToken<List<PuntoDeInteres>>() {
						}.getType());
				ArrayList<PuntoDeInteres> puntosDeInteres = (ArrayList<PuntoDeInteres>) myTypes;
				controlador.getSesion().setMisPDI(puntosDeInteres);
				Toast.makeText(this, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(this, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
			}
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	private ControladorSesion controladorSesion = ControladorSesion.getInstance();
	private Sesion sesion = null;
	String correo = "";
	String contrasenia ="";

}
