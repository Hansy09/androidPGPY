package com.wikitude.example;

import java.util.ArrayList;

public class BuscadorPDI {

	private ArrayList<PuntoDeInteres> pdiLista = null;

	public void demo() {		
		pdiLista = ControladorPDIs.getInstance().getPuntosDeInteres();
		System.out.println(pdiLista.isEmpty());
		System.out.println(pdiLista);
	}
}
