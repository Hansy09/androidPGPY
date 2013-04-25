package fmat.pgpy.team1.operadores;


import java.util.ArrayList;

import fmat.pgpy.team1.controladores.ControladorPDIs;
import fmat.pgpy.team1.dominio.Posicion;
import fmat.pgpy.team1.dominio.PuntoDeInteres;

public class CalculadoraGeografica {

	
private CalculadoraGeografica(){
		
	}
	
	public static CalculadoraGeografica getInstance(){
		if (calculadora==null){
			calculadora = new CalculadoraGeografica();
		}
		return calculadora;
	}
	
	public double calcularDistancia(Posicion posActual, Posicion posPdi){
		final int R = 6371;
		Double latDistance = toRad(posPdi.getLatitud() -posActual.getLatitud());
        Double lonDistance = toRad(posPdi.getLongitud()-posActual.getLongitud());
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + 
                   Math.cos(toRad(posActual.getLatitud())) * Math.cos(toRad(posPdi.getLatitud())) * 
                   Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double distance = R * c;
		return distance;
	}
	
	public void actualizarDistanciaLista(ArrayList<PuntoDeInteres> pdis){
		ControladorPDIs controlador= ControladorPDIs.getInstance();
		Posicion posActual = new Posicion();
		posActual.setLatitud(controlador.getLatitudActual());
		posActual.setLongitud(controlador.getLongitudActual());
		double distancia;
		for(int i=0; i<pdis.size();i++){
			distancia=calcularDistancia(posActual, pdis.get(i).getPosicion());
			distancia=redondear(distancia);
			pdis.get(i).setDistancia(distancia);
		}
	}
	private double redondear(double numero)
	{
	       return Math.rint(numero*100)/100;
	}
	
	private static Double toRad(Double value) {
		
        return value * Math.PI / 180;
    }
	

	private static CalculadoraGeografica calculadora=null;
}
