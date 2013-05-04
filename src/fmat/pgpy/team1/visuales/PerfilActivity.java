package fmat.pgpy.team1.visuales;



import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import fmat.pgpy.team1.R;
import fmat.pgpy.team1.controladores.ControladorSesion;
import fmat.pgpy.team1.dominio.Sesion;
import fmat.pgpy.team1.interfaces.RespuestaInterface;
import fmat.pgpy.team1.operadores.GestorServer;
import fmat.pgpy.team1.visuales.resource.ImageLoader;

/**
 * 
 * @Nombre SMD
 * @Fecha 01/03/2013
 * @Descripcion Clase encargada de la actividad del perfil
 *
 */
public class PerfilActivity extends Activity implements RespuestaInterface{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil);
		Sesion sesion = ControladorSesion.getInstance().getSesion();		
		new GestorServer().establecerDatosDePerfilEnSesion(sesion, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_perfil, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		switch (itemId) {
		case R.id.menu_Reg:
			this.visualizarRegistro();
			break;
		case R.id.menu_milista:
			this.visualizarMiLista();
			break;
		case R.id.menu_Favo:
			this.visualizarMisFavoritos();
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void mostrarActualizarPerfil(View view){				
		startActivity(new Intent(this, ActualizarPerfilActivity.class));
		finish();
	}	
	

	private void visualizarMiLista() {
		Intent intent = new Intent(this, ListaMisPDIActivity.class);
		// intent.putStringArrayListExtra(LISTA_PDI, poiBeanList);
		startActivity(intent);
	}
	private void visualizarMisFavoritos() {
		Intent intent = new Intent(this, ListaMisFavoritosActivity.class);
		// intent.putStringArrayListExtra(LISTA_PDI, poiBeanList);
		startActivity(intent);
	}
	
	
	private void visualizarRegistro() {
		Intent intent = new Intent(this, RegistroPDIActivity.class);
		// intent.putStringArrayListExtra(LISTA_PDI, poiBeanList);
		startActivity(intent);

	}
	
	@Override
    /**
	 * Metodo que recibe el objeto json respuesta del servidor y realiza la correspondiente accion segun la respuesta
	 */
	public void procesarRespuestaServidor(JSONObject jObject) {
		String tipoRespuesta;
		try {
			tipoRespuesta = jObject.get("codigo").toString();
			if (tipoRespuesta.equals("100")) {			
				
				JSONObject objeto = (JSONObject) jObject.get("objeto");
				
				Sesion sesion = ControladorSesion.getInstance().getSesion();
				sesion.setNombre(objeto.getString("nombre"));									
				sesion.setApellido(objeto.getString("apellido"));
				sesion.setCorreo(objeto.getString("correo electronico"));
				sesion.setURLImagen(objeto.getString("URLimagen"));
				
				establecerDatosDePerfilEnLayout();
			} else
				Toast.makeText(this, jObject.getString("mensaje"), Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	private void establecerDatosDePerfilEnLayout(){
			
		
		TextView elNombreDelUsuario = (TextView)findViewById(R.id.textView2);
		TextView elApellidoDelUsuario = (TextView)findViewById(R.id.textView4);
		TextView elCorreoDelUsuario = (TextView) findViewById(R.id.textView6);
		ImageView imagenDePerfil = (ImageView)findViewById(R.id.imageView1);				
		
		Sesion sesion = ControladorSesion.getInstance().getSesion();
		
		elNombreDelUsuario.setText(sesion.getNombre());
		
		elApellidoDelUsuario.setText(sesion.getApellido());
		
		elCorreoDelUsuario.setText(sesion.getCorreo());
		
		int loader = R.drawable.images;			
		String image_url = sesion.getURLImagenDelUsuario();
		ImageLoader imgLoader = new ImageLoader(this.getApplicationContext());
		imgLoader.DisplayImage(image_url, loader, imagenDePerfil);
		imagenDePerfil.setMinimumHeight(243);
		imagenDePerfil.setMinimumWidth(207);
		imagenDePerfil.setMaxHeight(243);
		imagenDePerfil.setMaxWidth(207);
	}
	
	@Override
	public void mostrarMensaje(String mensaje) {
		Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
		
	}

}
