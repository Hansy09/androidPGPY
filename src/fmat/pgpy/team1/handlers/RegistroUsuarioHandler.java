package fmat.pgpy.team1.handlers;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import fmat.pgpy.team1.visuales.RegistrarUsuarioActivity;

public class RegistroUsuarioHandler extends JsonHttpResponseHandler {
	
	public RegistroUsuarioHandler(RegistrarUsuarioActivity act) {
		// TODO Auto-generated constructor stub
		this.activity = act;
	}
	
    @Override
    public void onSuccess(JSONObject jObject) {
    	String tipoRespuesta;
		try {
			tipoRespuesta = jObject.get("codigo").toString();
		if(tipoRespuesta.equals("100")){
			Toast.makeText(activity, "Usuario Registrado",Toast.LENGTH_SHORT).show();
		} else
			Toast.makeText(activity, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    public void onFailure(Throwable arg0){
    	Toast.makeText(activity, "Hubo un problema con la conexion al servidor",Toast.LENGTH_SHORT).show();
    }
    
    private Activity activity=null;
}
