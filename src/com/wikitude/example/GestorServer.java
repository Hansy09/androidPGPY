package com.wikitude.example;



import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class GestorServer {

//	private String direccionBase = "http://192.168.1.67:8000/"; //Josue	
//	private String direccionBase = "http://192.168.1.83:8000/";  //Rusel
	private String direccionBase = "http://jd732.o1.gondor.io/"; 

	public void buscarDentroDeRangoMax(Posicion posicion, double rangoMaximo,
			VisorInterface visor) {
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams rp = new RequestParams();

		rp.put("longitud", String.valueOf(posicion.getLongitud()));
		rp.put("latitud", String.valueOf(posicion.getLatitud()));
		rp.put("rangoMaximoAlcance", String.valueOf(rangoMaximo));
		client.post(direccionBase + "/geoAdds/pdi/lista/", rp,
				new RespuestaHandler(visor));

	}

	public void buscarPDIsPorCategoria(Posicion posicion, double rangoMaximo,
			String clave, String categoria, VisorInterface visor) {		
		
		AsyncHttpClient httpClient = new AsyncHttpClient();
		RequestParams peticion = new RequestParams();
		peticion.put("longitud", String.valueOf(posicion.getLongitud()));
		peticion.put("latitud", String.valueOf(posicion.getLatitud()));
		peticion.put("rangoMaximoAlcance", "100");
		peticion.put("searchString", clave);
		peticion.put("categoria", categoria);  
		httpClient.post(direccionBase + "/geoAdds/pdi/categoria/", peticion,
				new RespuestaHandler(visor));		
		
		System.out.println("buscarPDIsPorCategoria");		
	}
}
