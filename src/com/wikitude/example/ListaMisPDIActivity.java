package com.wikitude.example;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class ListaMisPDIActivity extends Activity implements ToastInterface, RespuestaInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mis_pdi);
        ListView listView = (ListView) findViewById(R.id.listView1);
        customAdapter = new AdaptadorListPDI(controlador.getPuntosDeInteres(),this);
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
    
    
    @Override
    /**
	 * Metodo que recibe el objeto json respuesta del servidor y realiza la correspondiente accion segun la respuesta
	 */
	public void procesarRespuestaServidor(JSONObject jObject) {
		
		try {
			String tipoRespuesta=jObject.get("codigo").toString();
			if(tipoRespuesta.equals("100")){
				mostrarMensaje(jObject.get("mensaje").toString());
				ArrayList<PuntoDeInteres> pdis=controlador.getPuntosDeInteres();
				Gson gson = new Gson();
				PuntoDeInteres pdiEliminado=  gson.fromJson(jObject.getString("objeto"),PuntoDeInteres.class);
				System.out.println("El id a eliminar es: "+pdiEliminado.getId());
				PuntoDeInteres pdi = null;
				for(int i=0;i<pdis.size();i++ ){
					if(pdis.get(i).getId()==pdiEliminado.getId()){
						System.out.println("Encontre el de eliminar");
						pdi=pdis.get(i);
						pdis.remove(pdi);
						controlador.setPuntosDeInteres(pdis);
						controlador.actualizarJSONArrayPDIs();
						customAdapter.notifyDataSetChanged();
					}
				}
				
			}else{
				mostrarMensaje(jObject.get("mensaje").toString());
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Error con el json de eliminar de server");
			e.printStackTrace();
		}
	}
    
    private ControladorPDIs controlador= ControladorPDIs.getInstance();
    private AdaptadorListPDI customAdapter;
	
    
}
