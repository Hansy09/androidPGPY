package fmat.pgpy.team1.visuales;



import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import fmat.pgpy.team1.R;
import fmat.pgpy.team1.controladores.ControladorPDIs;
import fmat.pgpy.team1.controladores.ControladorSesion;
import fmat.pgpy.team1.dominio.Posicion;
import fmat.pgpy.team1.dominio.PuntoDeInteres;
import fmat.pgpy.team1.interfaces.RespuestaInterface;
import fmat.pgpy.team1.interfaces.ToastInterface;
/**
 * Clase que sirve como formulario para registrar un Punto de interes
 * @author Hansy
 *
 */
public class RegistroPDIActivity extends Activity implements ToastInterface, RespuestaInterface{

	@Override
	/**
	 * Metodo que carga el formulario de un punto de interes en la pantalla y llena el spinner de la categoria
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro_pdi);
		Spinner spinner_categorias = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<?> spinner_adapter = ArrayAdapter.createFromResource( this, R.array.categorias , android.R.layout.simple_spinner_item);
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_categorias.setAdapter(spinner_adapter);
	}

	@Override
	/**
	 * Metodo que crea las opciones del menu
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_registro_pdi, menu);
		return true;
	}
	
	/**
	 * Metodo que es llamado por el boton registrar que valida los campos ingresados y registra el pdi
	 * @param v El boton que desencadena el evento
	 */
	public void onRegistroPDI(View v){
		
		
		
		ControladorSesion conSesion = ControladorSesion.getInstance();
		String usuario = conSesion.getSesion().getCorreo();		 
		String nombre= ((EditText)this.findViewById(R.id.editText1)).getText().toString();
		Spinner mySpinner = (Spinner)findViewById(R.id.spinner1);
		int catego=(int) mySpinner.getSelectedItemId()+1;
		String categoria=String.valueOf(catego);
		if(validarCampos("Nombre",nombre)){
			PuntoDeInteres pdi = new PuntoDeInteres();
			pdi.setNombre(nombre);
			pdi.setCategoria(categoria);
			Posicion posicion = new Posicion();
			posicion.setAltitud(controlador.getAltitudActual());
			posicion.setLatitud(controlador.getLatitudActual());
			posicion.setLongitud(controlador.getLongitudActual());
			pdi.setPosicion(posicion);
			controlador.registrarPDI(usuario,pdi, this);
		}
	}
		
	/**
	 * Metodo para validar si esta vacio un campo y tirar un mensaje de error si
	 * es el caso
	 * 
	 * @param nombreCampo
	 *            Nombre del campo que saldra en el mensaje
	 * @param campo
	 *            El valor a validar
	 * @return True si esta correcto y false si esta incorrecto.
	 */
	private boolean validarCampos(String nombreCampo, String campo) {
		if (campo.trim().equals("")) {
			Toast.makeText(this,
					"El campo " + nombreCampo + " no puede estar vacio.",
					Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}



	@Override
	/**
	 * Metodo que muestra el mensaje en el activity
	 */
	public void mostrarMensaje(String mensaje) {
		// TODO Auto-generated method stu
	    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
	    
	}

	
	@Override
	/**
	 * Metodo que recibe el objeto json respuesta del servidor y realiza la correspondiente accion segun la respuesta
	 */
	public void procesarRespuestaServidor(JSONObject jObject) {
		
		try {
			String tipoRespuesta=jObject.get("codigo").toString();
			System.out.println("Llego respuesta");
			if(tipoRespuesta.equals("100")){
				mostrarMensaje(jObject.get("mensaje").toString());
				System.out.println("el mensaje recibido: "+jObject.get("mensaje").toString());
				Gson gson = new Gson();
				PuntoDeInteres pdiRegistrado=  gson.fromJson(jObject.getString("objeto"),PuntoDeInteres.class);
				contSesion.getSesion().getMisPDI().add(pdiRegistrado);
				controlador.getPuntosDeInteres().add(pdiRegistrado);
				controlador.actualizarJSONArrayPDIs();
				finish();
			}else{
				mostrarMensaje(jObject.get("mensaje").toString());
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	private ControladorPDIs controlador = ControladorPDIs.getInstance();
	private ControladorSesion contSesion = ControladorSesion.getInstance();
	
	
	
}
