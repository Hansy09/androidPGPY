package com.wikitude.example;

import java.io.File;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

public class ActualizarPDIActivity extends Activity implements ToastInterface {

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
	
	public void onActualizarPDI(View v){
		String usuario="hdse09@gmail.com";
		/**
		 * ControladorSesion conSesion = ControladorSesion.getSesion();
		 * usuario = conSesion.getSesion().getCorreo();
		 * 
		 */
		String descripcion=((EditText) this.findViewById(R.id.editText4)).getText().toString();
		String direccion=((EditText) this.findViewById(R.id.editText6)).getText().toString();
		String telefono=((EditText) this.findViewById(R.id.editText5)).getText().toString();
		String url=((EditText) this.findViewById(R.id.editText2)).getText().toString();
		String email=((EditText) this.findViewById(R.id.editText3)).getText().toString();
		pdi.setDescripcion(descripcion);
		pdi.setDireccion(direccion);
		pdi.setEmail(email);
		pdi.setTelefono(telefono);
		pdi.setUrl(url);
		controlador.actualizarPDI(usuario, pdi, this);
	}
	
	
	/**
	 * Metodo que se llama cuando se presiona el boton de tomar foto que realice el proceso de tomar y guardar la foto
	 * @param v Boton que desencadena el evento
	 *//*
	public void tomarFoto(View v){
		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		  intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getArchivoTemporal(this)) ); 
		  startActivityForResult(intent, TAKE_PHOTO_CODE);
	}
	
	*//**
	 * Metodo que sirve para obtener la imagen guardada
	 * @param context El contexto actual de la activity
	 * @return La imagen temporal guardada en la aplicacion
	 *//*
	private File getArchivoTemporal(Context context){
		  //it will return /sdcard/image.tmp
		  final File path = new File( Environment.getExternalStorageDirectory(), context.getPackageName() );
		  if(!path.exists()){
		    path.mkdir();
		  }
		  return new File(path, "image.tmp");
		}
	
	*//**
	 * Metodo que es llamado una vez tomada la foto que guarda el resultado en una variable global llamada archivoSeleccionado
	 *//*
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode == RESULT_OK) {
		    switch(requestCode){
		      case TAKE_PHOTO_CODE:
		        archivoSeleccionado = getArchivoTemporal(this);
		        
		        if(archivoSeleccionado.exists()){

		            Bitmap myBitmap = BitmapFactory.decodeFile(archivoSeleccionado.getAbsolutePath());
		            ImageView imagen=(ImageView)this.findViewById(R.id.imageView1);
		            imagen.setImageBitmap(myBitmap);

		        }
		      break;
		    }
		  }
	}*/
	
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
	
	/**
	 * Metodo que carga en el visual los datos del punto de interes a modificar
	 */
	private void cargarDatosPDI(){
		String idS = this.getIntent().getExtras().getString("id");
		int id = Integer.parseInt(idS);
		ControladorPDIs controlador= ControladorPDIs.getInstance();
		ArrayList<PuntoDeInteres> puntosDeInteres=controlador.getPuntosDeInteres();
		for(int i=0;i<puntosDeInteres.size();i++){
			if(puntosDeInteres.get(i).getId()==id){
				pdi=puntosDeInteres.get(i);
			}
		}
		if(pdi!=null){
			((TextView) this.findViewById(R.id.textView21)).setText(pdi.getNombre());
			((TextView) this.findViewById(R.id.textView22)).setText(pdi.getCategoria());
			((EditText) this.findViewById(R.id.editText4)).setText(pdi.getDescripcion());
			((EditText) this.findViewById(R.id.editText6)).setText(pdi.getDireccion());
			((EditText) this.findViewById(R.id.editText5)).setText(pdi.getTelefono());
			((EditText) this.findViewById(R.id.editText2)).setText(pdi.getUrl());
			((EditText) this.findViewById(R.id.editText3)).setText(pdi.getEmail());
			
		}
	}
	/*
	private static final int TAKE_PHOTO_CODE = 1;
	private static File archivoSeleccionado=null;*/
	private ControladorPDIs controlador = ControladorPDIs.getInstance();
	private PuntoDeInteres pdi = new PuntoDeInteres();
	

}
