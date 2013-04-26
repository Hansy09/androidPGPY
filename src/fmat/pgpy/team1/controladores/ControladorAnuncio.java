package fmat.pgpy.team1.controladores;

import java.util.ArrayList;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import fmat.pgpy.team1.R;
import fmat.pgpy.team1.dominio.Anuncio;
import fmat.pgpy.team1.dominio.PuntoDeInteres;
import fmat.pgpy.team1.interfaces.RespuestaAlternativaInterface;
import fmat.pgpy.team1.interfaces.RespuestaInterface;
import fmat.pgpy.team1.operadores.GestorServer;
import fmat.pgpy.team1.visuales.AnuncioActivity;
import fmat.pgpy.team1.visuales.RegistrarAnuncioActivity;

/**
 * 
 * @Autor SMD
 * @Fecha 29/03/2013
 * @Descripcion Clase encargada del Controlador de Anuncios
 *
 */
public class ControladorAnuncio {

	public static ControladorAnuncio getInstance(){
		if (controladorAnuncio==null){
			controladorAnuncio = new ControladorAnuncio();
		}
		return controladorAnuncio;
	}
	
	private ControladorAnuncio(){
	}
	
	public void registrarAnuncio(Anuncio anuncio, int idPDI, RespuestaInterface act){
		GestorServer gestorServer = new GestorServer();
		Log.d("log", "Mandando a registrar a servidor");
		gestorServer.registrarAnuncioEnServidor(anuncio, idPDI, act);
	}
	
	public boolean confirmarCamposObligatorios(String titulo, String categoria, String descripcion){
		if(titulo.equals("") || categoria.equals("") || descripcion.equals(""))
			return false;
		else return true;
	}
	
	/**
	 * Envia una notificacion al usuario, para abrir la actividad del anuncio que se muestra
	 * OJO: Actualmente envia la notificacion del anuncio al registrarlo, y solo al usuario que
	 * la registro
	 * @param idPDI
	 * @param anuncio
	 * @param act
	 */
	public void enviarNotificacion(int idPDI, Anuncio anuncio, RegistrarAnuncioActivity act){
		ControladorPDIs controlador= ControladorPDIs.getInstance();
		ArrayList<PuntoDeInteres> puntosDeInteres=controlador.getPuntosDeInteres();
		PuntoDeInteres pdi= null;
		for(int i=0;i<puntosDeInteres.size();i++){
			if(puntosDeInteres.get(i).getId()==idPDI){
				pdi=puntosDeInteres.get(i);
			}
		}
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(act)
		        .setSmallIcon(R.drawable.icon)
		        .setContentTitle(anuncio.getTitulo())
		        .setContentText("Nuevo anuncio en el PDI "+pdi.getNombre())
		        .setAutoCancel(true);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(act, AnuncioActivity.class);
		resultIntent.putExtra("titulo", anuncio.getTitulo());
		resultIntent.putExtra("categoria", anuncio.getCategoria());
		resultIntent.putExtra("descripcion", anuncio.getDescripcion());
		resultIntent.putExtra("idPDI", idPDI);
		resultIntent.putExtra("notificacion", true);
		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(act);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(AnuncioActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) act.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(1, mBuilder.build());
	}
	
	public void actualizarAnuncio(Anuncio anuncio, int idPDI, RespuestaInterface act){
		GestorServer gestorServer = new GestorServer();
		gestorServer.actualizarAnuncioEnServidor(anuncio, idPDI, act);
	}
	
	public void eliminarAnuncio(int idAnuncio, int idPDI, RespuestaInterface act){
		GestorServer gestorServer = new GestorServer();
		gestorServer.eliminarAnuncioEnServidor(idAnuncio, idPDI, act);
	}
	
	/**
	 * Esta clase se utiliza para cualquier lugar donde se necesiten obtener los Anuncios de algun PDI
	 * ya registrado previamente
	 * @param idPDI
	 * @param act
	 */
	public void obtenerAnunciosDePDI(int idPDI, RespuestaAlternativaInterface act){
		GestorServer gestorServer = new GestorServer();
		gestorServer.obtenerAnunciosPDI(idPDI, act);
	}
	
	private static ControladorAnuncio controladorAnuncio;
}
