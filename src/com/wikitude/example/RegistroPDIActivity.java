package com.wikitude.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * Clase que sirve como formulario para registrar un Punto de interes
 * @author Hansy
 *
 */
public class RegistroPDIActivity extends Activity implements ToastInterface{

	@Override
	/**
	 * Metodo que carga el formulario de un punto de interes en la pantalla y llena el spinner de la categoria
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro_pdi);
		Spinner spinner_categorias = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.categorias , android.R.layout.simple_spinner_item);
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
		
		String usuario="hdse09@gmail.com";
		/**
		 * ControladorSesion conSesion = ControladorSesion.getSesion();
		 * usuario = conSesion.getSesion().getCorreo();
		 * 
		 */
		String nombre= ((EditText)this.findViewById(R.id.editText1)).getText().toString();
		Spinner mySpinner = (Spinner)findViewById(R.id.spinner1);
		String categoria = mySpinner.getSelectedItem().toString();
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
	public boolean validarCampos(String nombreCampo, String campo) {
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


	
	private ControladorPDIs controlador = ControladorPDIs.getInstance();
	
	
	
}
