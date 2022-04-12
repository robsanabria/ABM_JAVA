package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Factura implements Serializable {

	private double precioTotal;
	private ArrayList<Articulo> articulosComprados = new ArrayList<Articulo>();

	public Factura(double precioTotal, ArrayList<Articulo> articulosComprados) {
		this.precioTotal = precioTotal;
		this.articulosComprados = articulosComprados;
	}

}
