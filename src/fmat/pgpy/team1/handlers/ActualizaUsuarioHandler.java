package fmat.pgpy.team1.handlers;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import fmat.pgpy.team1.visuales.ActualizarPerfilActivity;
import fmat.pgpy.team1.visuales.PerfilActivity;

public class ActualizaUsuarioHandler extends JsonHttpResponseHandler {

	private Activity activity = null;

	public ActualizaUsuarioHandler(ActualizarPerfilActivity act) {

		this.activity = act;
	}

	@Override
	public void onSuccess(JSONObject jObject) {	
		System.out.println("ActualizaUsuarioHandler:\n"+jObject);
		String tipoRespuesta;
		try {
			tipoRespuesta = jObject.get("codigo").toString();
			if (tipoRespuesta.equals("100")) {
				Toast.makeText(activity, "Perfil de usuario actualizado",Toast.LENGTH_SHORT).show();
				activity.startActivity(new Intent(activity, PerfilActivity.class));
				activity.finish();								
			} else
				Toast.makeText(activity, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
			Toast.makeText(activity, "No se ha podido actualizar el perfil de usuario",Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

	public void onFailure(Throwable arg0) {
		Toast.makeText(activity, "Hubo un problema con la conexion al servidor",Toast.LENGTH_SHORT).show();
	}
}
