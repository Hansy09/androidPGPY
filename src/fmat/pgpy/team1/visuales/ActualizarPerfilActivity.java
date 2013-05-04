package fmat.pgpy.team1.visuales;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import fmat.pgpy.team1.R;
import fmat.pgpy.team1.controladores.ControladorSesion;
import fmat.pgpy.team1.dominio.PuntoDeInteres;
import fmat.pgpy.team1.dominio.Sesion;
import fmat.pgpy.team1.interfaces.RespuestaAlternativaInterface;
import fmat.pgpy.team1.interfaces.RespuestaInterface;
import fmat.pgpy.team1.operadores.GestorServer;
import fmat.pgpy.team1.visuales.resource.ImageLoader;

public class ActualizarPerfilActivity extends Activity implements RespuestaInterface, RespuestaAlternativaInterface{

	private final int TAKE_PHOTO_CODE = 1;

	private final int SELECT_PICTURE = 2;

	private String rutaArchivoImagen = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualizar_perfil);

		insertaInfoDePerfilEnLayout();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_actualizar_perfil, menu);
		return true;
	}

	public void insertaInfoDePerfilEnLayout() {
		Toast.makeText(this, "... Cargando ...", Toast.LENGTH_SHORT).show();
		TextView elNombreDelUsuario = (TextView) findViewById(R.id.txtFld_nombreUsuario);
		TextView elApellidoDelUsuario = (TextView) findViewById(R.id.txtFld_apellidoUsuario);
		ImageView imgPerfil = (ImageView) findViewById(R.id.img_imagenUsuario);

		Sesion sesion = ControladorSesion.getInstance().getSesion();

		elNombreDelUsuario.setText(sesion.getNombre());
		elApellidoDelUsuario.setText(sesion.getApellido());

		int loader = R.drawable.images;
		String image_url = sesion.getURLImagenDelUsuario();
		ImageLoader imgLoader = new ImageLoader(getApplicationContext());
		imgLoader.DisplayImage(image_url, loader, imgPerfil);
		imgPerfil.setMinimumHeight(243);
		imgPerfil.setMinimumWidth(207);
		imgPerfil.setMaxHeight(243);
		imgPerfil.setMaxWidth(207);
	}

	public void cambiarContrasenia(View view) {
		finish();
		startActivity(new Intent(this, CambiarContraseniaActivity.class));
	}

	public void tomarFoto(View view) {
		rutaArchivoImagen = Environment.getExternalStorageDirectory()+ "/imgPerfil.jpg";
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri output = Uri.fromFile(new File(rutaArchivoImagen));
		intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
		startActivityForResult(intent, TAKE_PHOTO_CODE);
	}

	public void seleccionarImagenDeGaleria(View view) {
		Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
		startActivityForResult(intent, SELECT_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			ImageView imgPerfil = (ImageView) findViewById(R.id.img_imagenUsuario);
			if (requestCode == TAKE_PHOTO_CODE) {
				System.out.println("___________________Ruta de la foto: "+ rutaArchivoImagen);
				imgPerfil.setImageBitmap(BitmapFactory.decodeFile(rutaArchivoImagen));
			}
			if (requestCode == SELECT_PICTURE) {
				Uri uriImagenSeleccionada = data.getData();
				Cursor cursor = getContentResolver().query(uriImagenSeleccionada, null, null, null, null);
				cursor.moveToFirst();
				int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
				rutaArchivoImagen = cursor.getString(idx);
				System.out.println("___________________Ruta de la imagen: " + rutaArchivoImagen);
				try {
					InputStream is = getContentResolver().openInputStream(uriImagenSeleccionada);
					BufferedInputStream bis = new BufferedInputStream(is);
					Bitmap bitmap = BitmapFactory.decodeStream(bis);
					imgPerfil.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					Toast.makeText(this, "Error al cargar la imagen",Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		}
	}

	public void actualizarPerfil(View view) throws FileNotFoundException {
		Sesion sesion = ControladorSesion.getInstance().getSesion();

		System.out.println("ActualizarPerfilActivity:\n"
				+ sesion.getURLImagenDelUsuario());

		String nuevoNombre = ((EditText) findViewById(R.id.txtFld_nombreUsuario)).getText().toString();
		sesion.setNombre(nuevoNombre);

		String nuevoApellido = ((EditText) findViewById(R.id.txtFld_apellidoUsuario)).getText().toString();
		sesion.setApellido(nuevoApellido);

		File archivoImagen = new File(rutaArchivoImagen);
		if (archivoImagen.exists()) {
			System.out.println("___________________El archivo de imagen existe");
			new GestorServer().subirImagenDePerfilAlServidor(archivoImagen, sesion.getCorreo(), this);
		}
		else{
			System.out.println("________________El archivo de no imagen existe");
			new GestorServer().actualizaUsuarioEnServidor(sesion, this); 			
		}
	}
	
	@Override
	public void procesarRespuestaServidor(JSONObject jObject) {
		// TODO Auto-generated method stub
		String tipoRespuesta;
		try {
			tipoRespuesta = jObject.get("codigo").toString();
			if (tipoRespuesta.equals("100")) {
				Toast.makeText(this, "Perfil de usuario actualizado",Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this, PerfilActivity.class));
				finish();								
			} else
				Toast.makeText(this, jObject.getString("mensaje"),Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
			Toast.makeText(this, "No se ha podido actualizar el perfil de usuario",Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	
	@Override
	public void procesarRespuestaAlternativaServidor(JSONObject jObject) {
		// TODO Auto-generated method stub
		String tipoRespuesta;			
		try {
			tipoRespuesta = jObject.get("codigo").toString();
			if (tipoRespuesta.equals("100")) {																
				
				String urlImagen =  jObject.getString("mensaje");
				
				Sesion sesion = ControladorSesion.getInstance().getSesion();					
				sesion.setURLImagen(urlImagen);									
									
				new GestorServer().actualizaUsuarioEnServidor(sesion, this);
			} else
				Toast.makeText(this, jObject.getString("mensaje"), Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
			e.printStackTrace();
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
}
