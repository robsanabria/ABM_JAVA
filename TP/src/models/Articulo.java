package models;

import java.io.Serializable;

public class Articulo implements Serializable {
	String CodigoArticulo;
	String NombreArticulo;
	@Override
	public String toString() {
		return "Articulo [CodigoArticulo= " + CodigoArticulo + ", NombreArticulo= " + NombreArticulo
				+ ", CantidadArticulo= " + CantidadArticulo + ", PrecioArticulo= " + PrecioArticulo + "]";
	}

	public String getCodigoArticulo() {
		return CodigoArticulo;
	}

	public void setCodigoArticulo(String codigoArticulo) {
		CodigoArticulo = codigoArticulo;
	}

	public String getNombreArticulo() {
		return NombreArticulo;
	}

	public void setNombreArticulo(String nombreArticulo) {
		NombreArticulo = nombreArticulo;
	}

	public int getCantidadArticulo() {
		return CantidadArticulo;
	}

	public void setCantidadArticulo(int cantidadArticulo) {
		CantidadArticulo = cantidadArticulo;
	}

	public double getPrecioArticulo() {
		return PrecioArticulo;
	}

	public void setPrecioArticulo(double precioArticulo) {
		PrecioArticulo = precioArticulo;
	}

	int CantidadArticulo;
	double PrecioArticulo;

	public Articulo(String codigoArticulo, String NombreArticulo, double PrecioArticulo, int CantidadArticulo) {
		super();
		this.CodigoArticulo = codigoArticulo;
		this.NombreArticulo = NombreArticulo;
		this.PrecioArticulo = PrecioArticulo;
		this.CantidadArticulo = CantidadArticulo;
	}
}