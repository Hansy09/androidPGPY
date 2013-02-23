package com.wikitude.example;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import com.wikitude.architect.ArchitectUrlListener;
import com.wikitude.architect.ArchitectView;

/**
 * 
 * @author Wikitude
 * @date JAN 2012
 * 
 * @class SimpleARBrowserActivity
 * 
 *        sample application to show how to use the ARchitect SDK loads simple
 *        pois via javascript into the ARchitect world and displays them
 *        accordingly displays a bubble with information about the selected poi
 *        on the screen and displays a detail page when the bubble is clicked
 *        uses Android's LocationManager to get updates on the user's location
 * 
 *        important is that the methods of the activity lifecycle are forwarded
 *        to the ArchitectView Important methods: onPostCreate() onResume()
 *        onPause() onDestroy() onLowMemory()
 * 
 *        Please also have a look at the application's Manifest and layout
 *        xml-file to see the permissions and requirements an activity using the
 *        SDK has to possess. (REF: ARchitect Documentation)
 */
public class SimpleARBrowserActivity extends Activity implements
		ArchitectUrlListener, LocationListener, VisorInterface {

	private static final String TAG = SimpleARBrowserActivity.class
			.getSimpleName();

	private final static float TEST_LATITUDE = 47.77318f;
	private final static float TEST_LONGITUDE = 13.069730f;
	private final static float TEST_ALTITUDE = 150;
	private  double longitudActual = 13.069730f;
	private  double latitudActual = 47.77318f;

	private String apiKey = "n+DtduXJkBa4hwW4Yhfhl6VjAbR0s8Bu+cLAvUYkENtRNfOIL96dDpAK1saHrVCG8D2IR2elw/AZda7r+Z9Gi9OhV/p+4qrNDctU0FRJipzBmGAC7A3Ro74mTk3uvPBv4RKF62H1e5bQbBpw669Jm+1ML9i1aEa9XBTtVrKtaNxTYWx0ZWRfXz3NUfI/Oou3sPI6XQQqnn8jxfaY39n7P/WT3wUj6AHLQa44pS5bVkk+YIUYiu5lrn2DFtG6wNQPk1KgOngpWihJH4IH3xstZl/CJHd6xPI279toJrakn5FWdL3LtDObTtWFI5qCuJttCRiWiZ/hd1lLx7BYyDTxhXCotN+ph5keUquN/cKNQjSJ/AnlvBcDV7NMmqBmBFzi2wJhte1WHnr80OjAw1oBPVT2+uUSCJxX5UyHygGx9qbvFgFVHclrXdalGqOwqQNauKiZF5QslSMfMYgFdWOvQgjDN1RbfTkUaaHJrW36nz2pz2JH2rVlQNN6P6EZZcOViF7H0L4MMQtm3+EqNE/4QEcW/Ir5e6hOzEeXZUx9LlRe8tIoxf50HhR8RfHKmjY0D9bDtVEDQyGD7NjPVJL+fddoEvTlrP5O5TaUSYC3BEd8uXTMxpUFVMfaEezbRQ/lcAF96gSmbkY1DHwgExsqiHs81Czbmfu+GOj6S2mnVDxnBsUF9ZXhg6+GM+0Uqfyk";

	private ArchitectView architectView=null;
	private LocationManager locManager=null;
	private ControladorPDIs controlador = ControladorPDIs.getInstance();
	private SeekBar seekbarRango = null;
	private double distanciaSeleccionada = 7.5;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// let the application be fullscreen
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// check if the device fulfills the SDK'S minimum requirements
		if (!ArchitectView.isDeviceSupported(this)) {
			Toast.makeText(this, "minimum requirements not fulfilled",
					Toast.LENGTH_LONG).show();
			this.finish();
			return;
		}
		setContentView(R.layout.main);

		// set the devices' volume control to music to be able to change the
		// volume of possible soundfiles to play
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.architectView = (ArchitectView) this
				.findViewById(R.id.architectView);
		// onCreate method for setting the license key for the SDK
		architectView.onCreate(apiKey);

		// in order to inform the ARchitect framework about the user's location
		// Androids LocationManager is used in this case
		// NOT USED IN THIS EXAMPLE
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				this);

		seekbarRango = (SeekBar) findViewById(R.id.seekBarRango);

		seekbarRango.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				ajustarRango(seekBar.getProgress());
				calcularNuevoRango();
				
				loadSampleWorld();
				Log.d("probando al soltar", "SeekBar: " + seekBar.getProgress());
			}
		});

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// IMPORTANT: creates ARchitect core modules
		if (this.architectView != null)
			this.architectView.onPostCreate();

		// register this activity as handler of "architectsdk://" urls
		this.architectView.registerUrlListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();

		this.architectView.onResume();
		this.architectView.setLocation(TEST_LATITUDE, TEST_LONGITUDE,
				TEST_ALTITUDE, 1f);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (this.architectView != null)
			this.architectView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (this.architectView != null)
			this.architectView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();

		if (this.architectView != null)
			this.architectView.onLowMemory();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.visor_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		switch (itemId) {
		case R.id.menu_Lista:
			this.visualizarLista();
			break;
		case R.id.menu_BusqAv:
			// Insertar cosas
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * <p>
	 * interface method of {@link ArchitectUrlListener} class called when an url
	 * with host "architectsdk://" is discovered
	 * 
	 * can be parsed and allows to react to events triggered in the ARchitect
	 * World
	 * </p>
	 */
	@Override
	public boolean urlWasInvoked(String url) {
		// parsing the retrieved url string
		List<NameValuePair> queryParams = URLEncodedUtils.parse(
				URI.create(url), "UTF-8");

		String id = "";
		// getting the values of the contained GET-parameters
		for (NameValuePair pair : queryParams) {
			if (pair.getName().equals("id")) {
				id = pair.getValue();
			}
		}

		// get the corresponding poi bean for the given id
		/*
		 * PoiBean bean = poiBeanList.get(Integer.parseInt(id)); // start a new
		 * intent for displaying the content of the bean Intent intent = new
		 * Intent(this, PoiDetailActivity.class); intent.putExtra("POI_NAME",
		 * bean.getName()); intent.putExtra("POI_DESC", bean.getDescription());
		 * this.startActivity(intent);
		 */

		Intent intent = new Intent(this, PDIDetalle.class);
		intent.putExtra("id", id);
		this.startActivity(intent);
		return true;
	}


	/**
	 * loads a sample architect world and creates a definable amount of pois in
	 * beancontainers and converts them into a jsonstring that can be sent to
	 * the framework
	 * 
	 * @throws IOException
	 *             exception thrown while loading an Architect world
	 */
	public void loadSampleWorld() {
		try {
			this.architectView.load("tutorial1.html");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.architectView.callJavascript("newData("
				+ controlador.getPuntosDeInteresJArray() + "," + latitudActual
				+ "," + longitudActual + "," + distanciaSeleccionada + ");");

	}


	/**
	 * listener method called when the location of the user has changed used for
	 * informing the ArchitectView about a new location of the user
	 */
	@Override
	public void onLocationChanged(Location loc) {
		// IMPORTANT:
		// use this method for informing the SDK about a location change by the
		// user
		// for simplicity not used in this example

		// inform ArchitectView about location changes
		if (this.architectView != null) {
			this.architectView.setLocation((float) (loc.getLatitude()),
					(float) (loc.getLongitude()), loc.getAccuracy());
			if (latitudActual != loc.getLatitude()
					|| longitudActual != loc.getLongitude()) {
				longitudActual = loc.getLongitude();
				latitudActual = loc.getLatitude();
				Posicion posicion = new Posicion();
				posicion.setLongitud(longitudActual);
				posicion.setLatitud(latitudActual);

				controlador.filtrarPDIsCercanos(posicion, 15, this);
			}

		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	public void realizarBusqueda(View view) {
		Intent intent = new Intent(this, BusquedaSimpleActivity.class);
		EditText editText = (EditText) findViewById(R.id.textBuscar);
		String message = editText.getText().toString();
		intent.putExtra("clave", message);
		startActivity(intent);
	}

	public void visualizarLista() {
		Intent intent = new Intent(this, ListaPDIsActivity.class);
		// intent.putStringArrayListExtra(LISTA_PDI, poiBeanList);
		startActivity(intent);

	}

	public void ajustarRango(int rangoBarra) {
		if (rangoBarra == 0)
			distanciaSeleccionada = .05;
		else
			distanciaSeleccionada = (rangoBarra * .15);
		
	}

	public double obtenerdistanciaSeleccionada() {
		return distanciaSeleccionada;
	}

	public double calcularNuevoRango() {
		Log.d("calculando distancia", "Distancia Seleccionada: "
				+ obtenerdistanciaSeleccionada());
		return obtenerdistanciaSeleccionada();
	}

}