package fmat.pgpy.team1.visuales;


import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import fmat.pgpy.team1.R;
import fmat.pgpy.team1.controladores.ControladorPDIs;
import fmat.pgpy.team1.controladores.ControladorSesion;
import fmat.pgpy.team1.dominio.PuntoDeInteres;
import fmat.pgpy.team1.interfaces.RespuestaInterface;
import fmat.pgpy.team1.interfaces.ToastInterface;

public class ActualizarPDIActivity extends Activity implements ToastInterface , RespuestaInterface{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actulizar_pdi);

		this.cargarDatosPDI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_actulizar_pdi, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		switch (itemId) {
		case R.id.menu_RegistrarAnuncio:
			this.registrarAnuncio();
			break;
		case R.id.menu_VerAnunciosPDI:
			this.verAnuncios();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void registrarAnuncio() {
		Intent intent = new Intent(this, RegistrarAnuncioActivity.class);
		intent.putExtra("idPDI", pdi.getId());
		startActivity(intent);
	}
	
	/**
	 * Muestra los anuncios segun el PDI que se haya seleccionado previamente, 
	 * con la variable numeroPDI
	 */
	public void verAnuncios(){
		Intent intent = new Intent(this, ListaMisAnunciosPDIActivity.class);
		intent.putExtra("idPDI", pdi.getId());
		startActivity(intent);		
	}

	public void onActualizarPDI(View v) {
		String usuario = "hdse09@gmail.com";
		
		  ControladorSesion conSesion = ControladorSesion.getInstance(); 
		  usuario= conSesion.getSesion().getCorreo();
		
		String descripcion = ((EditText) this.findViewById(R.id.editText4))
				.getText().toString();
		String direccion = ((EditText) this.findViewById(R.id.editText6))
				.getText().toString();
		String telefono = ((EditText) this.findViewById(R.id.editText5))
				.getText().toString();
		String url = ((EditText) this.findViewById(R.id.editText2)).getText()
				.toString();
		String email = ((EditText) this.findViewById(R.id.editText3)).getText()
				.toString();
		if(this.existeCambiosEnPDI()){
			if (url.matches("^(www\\.)[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&:/~\\+#!]*[\\w\\-\\@?^=%&/~\\+#])?") || url.trim().equals("")) {
				if (email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")|| email.trim().equals("")) {
					if (telefono.matches("^[0-9]{1,10}$") || telefono.trim().equals("")) {

						pdi.setDescripcion(descripcion);
						pdi.setDireccion(direccion);
						pdi.setEmail(email);
						pdi.setTelefono(telefono);
						pdi.setUrl(url);
						ArrayList<PuntoDeInteres> pdis=controlador.getPuntosDeInteres();
						for(int i=0;i<pdis.size();i++ ){
							if(pdis.get(i).getId()==pdi.getId()){
								pdis.remove(pdi);
								pdis.add(pdi);
								controlador.setPuntosDeInteres(pdis);
								controlador.actualizarJSONArrayPDIs();
							}
						}
						controlador.actualizarPDI(usuario, pdi, this);
						Intent intent = new Intent(this, PDIDetalle.class);
						System.out.println("El id es: "+pdi.getId());
						intent.putExtra("id", String.valueOf(pdi.getId()));
						this.startActivity(intent);
					} else {
						Toast.makeText(this,
								"El campo telefono no es un telefono valido.",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(this,
							"El campo correo no es un correo valido.",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, "El campo url no es una url valida.",
						Toast.LENGTH_SHORT).show();
			}
			
		}else{
			Toast.makeText(this,
					"No se realizaron cambios.",
					Toast.LENGTH_SHORT).show();
		}

	}
	
	private boolean existeCambiosEnPDI(){
		String descripcion = ((EditText) this.findViewById(R.id.editText4))
				.getText().toString();
		String direccion = ((EditText) this.findViewById(R.id.editText6))
				.getText().toString();
		String telefono = ((EditText) this.findViewById(R.id.editText5))
				.getText().toString();
		String url = ((EditText) this.findViewById(R.id.editText2)).getText()
				.toString();
		String email = ((EditText) this.findViewById(R.id.editText3)).getText()
				.toString();
		if(pdi.getDescripcion().trim().equals(descripcion.trim())&&pdi.getDireccion().trim().equals(direccion.trim())&&pdi.getEmail().trim().equals(email.trim())&&pdi.getTelefono().trim().equals(telefono.trim())&&pdi.getUrl().trim().equals(url.trim())){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * Metodo que se llama cuando se presiona el boton de tomar foto que realice
	 * el proceso de tomar y guardar la foto
	 * 
	 * @param v
	 *            Boton que desencadena el evento
	 */
	/*
	 * public void tomarFoto(View v){ final Intent intent = new
	 * Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	 * intent.putExtra(MediaStore.EXTRA_OUTPUT,
	 * Uri.fromFile(getArchivoTemporal(this)) ); startActivityForResult(intent,
	 * TAKE_PHOTO_CODE); }
	 *//**
	 * Metodo que sirve para obtener la imagen guardada
	 * 
	 * @param context
	 *            El contexto actual de la activity
	 * @return La imagen temporal guardada en la aplicacion
	 */
	/*
	 * private File getArchivoTemporal(Context context){ //it will return
	 * /sdcard/image.tmp final File path = new File(
	 * Environment.getExternalStorageDirectory(), context.getPackageName() );
	 * if(!path.exists()){ path.mkdir(); } return new File(path, "image.tmp"); }
	 *//**
	 * Metodo que es llamado una vez tomada la foto que guarda el resultado
	 * en una variable global llamada archivoSeleccionado
	 */
	/*
	 * public void onActivityResult(int requestCode, int resultCode, Intent
	 * data){ if (resultCode == RESULT_OK) { switch(requestCode){ case
	 * TAKE_PHOTO_CODE: archivoSeleccionado = getArchivoTemporal(this);
	 * 
	 * if(archivoSeleccionado.exists()){
	 * 
	 * Bitmap myBitmap =
	 * BitmapFactory.decodeFile(archivoSeleccionado.getAbsolutePath());
	 * ImageView imagen=(ImageView)this.findViewById(R.id.imageView1);
	 * imagen.setImageBitmap(myBitmap);
	 * 
	 * } break; } } }
	 */

	

	@Override
	/**
	 * Metodo que muestra el mensaje en el activity
	 */
	public void mostrarMensaje(String mensaje) {
		// TODO Auto-generated method stu
		Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

	}

	/**
	 * Metodo que carga en el visual los datos del punto de interes a modificar
	 */
	private void cargarDatosPDI() {
		String idS = this.getIntent().getExtras().getString("id");
		int id = Integer.parseInt(idS);
		ArrayList<PuntoDeInteres> puntosDeInteres = contSesion.getSesion()
				.getMisPDI();
		for (int i = 0; i < puntosDeInteres.size(); i++) {
			if (puntosDeInteres.get(i).getId() == id) {
				pdi = puntosDeInteres.get(i);
			}
		}
		if (pdi != null) {
			pdi.setId(id);
			((TextView) this.findViewById(R.id.textView21)).setText(pdi
					.getNombre());
			((TextView) this.findViewById(R.id.textView22)).setText(pdi
					.getCategoria());
			((EditText) this.findViewById(R.id.editText4)).setText(pdi
					.getDescripcion());
			((EditText) this.findViewById(R.id.editText6)).setText(pdi
					.getDireccion());
			((EditText) this.findViewById(R.id.editText5)).setText(pdi
					.getTelefono());
			((EditText) this.findViewById(R.id.editText2))
					.setText(pdi.getUrl());
			((EditText) this.findViewById(R.id.editText3)).setText(pdi
					.getEmail());

		}
	}
	
	
	@Override
	/**
	 * Metodo que recibe el objeto json respuesta del servidor y realiza la correspondiente accion segun la respuesta
	 */
	public void procesarRespuestaServidor(JSONObject jObject) {
		try {
			String tipoRespuesta=jObject.get("codigo").toString();
			if(tipoRespuesta.equals("100")){
				mostrarMensaje(jObject.get("mensaje").toString());
			}else{
				mostrarMensaje(jObject.get("mensaje").toString());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * private static final int TAKE_PHOTO_CODE = 1; private static File
	 * archivoSeleccionado=null;
	 */
	private ControladorPDIs controlador = ControladorPDIs.getInstance();
	private ControladorSesion contSesion = ControladorSesion.getInstance();
	private PuntoDeInteres pdi = new PuntoDeInteres();
}
