package com.wikitude.example;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class ListaMisPDIActivity extends Activity implements ToastInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mis_pdi);
        ListView listView = (ListView) findViewById(R.id.listView1);
        AdaptadorListPDI customAdapter = new AdaptadorListPDI(controlador.getPuntosDeInteres(),this);
        listView.setAdapter(customAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_lista_mis_pdi, menu);
        return true;
    }
    
    /**
     * Metodo que es llamado por el evento de borrar un punto d einteres
     * @param id El id del punto de interes seleccionado
     */
    public void onBorrarPDI(int id){
    	String usuario="hdse09@gmail.com";
    	controlador.borrarPDI(usuario, id, this);
    }
    
    @Override
	/**
	 * Metodo que muestra el mensaje en el activity
	 */
	public void mostrarMensaje(String mensaje) {
		// TODO Auto-generated method stu
	    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
	    
	}
    
    
    private ControladorPDIs controlador= ControladorPDIs.getInstance();

    
}
