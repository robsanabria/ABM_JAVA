package models;
import java.util.ArrayList;
import java.util.HashMap;

public class Cliente extends Usuario {

    public static HashMap<String, CuentaBancaria> cuentasBancaria;

    private ArrayList<Factura> facturas;

    public Cliente(String nombre, String constrasenia) {
        super(nombre, constrasenia);
        Cliente.cuentasBancaria = new HashMap<String, CuentaBancaria>();
        this.facturas = new ArrayList<Factura>();
    }

    public static HashMap<String,CuentaBancaria> getCuentasBancaria() {
        return cuentasBancaria;
    }

    public void setCuentasBancaria(HashMap<String, CuentaBancaria> cuentasBancaria) {
        this.cuentasBancaria = cuentasBancaria;
    }

    public ArrayList<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(ArrayList<Factura> facturas) {
        this.facturas = facturas;
    }

    public static boolean transferirDineroEntreCuentas(String idOrigen, String idDestino, double monto) {
      CuentaBancaria origen = Cliente.cuentasBancaria.get(idOrigen);
      CuentaBancaria destino = Cliente.cuentasBancaria.get(idDestino);

        if(origen != null && destino != null) {
            double origenDinero = origen.getDinero();
            if(origenDinero >= monto){
                origen.setDinero(origen.getDinero() - monto);
                destino.setDinero(destino.getDinero() + monto);
                return true;
            }
        }
        return false;
    }

    public static boolean eliminarCuenta(String idCuenta) {
        if (cuentasBancaria.remove(idCuenta) != null){
            return true;
        } else {
            return false;
        }
    }
    public boolean realizarPago(String id, double factura) {
    	if(this.cuentasBancaria.get(id).getDinero() >= factura) {   		
    		double nuevaValorcCuenta = this.cuentasBancaria.get(id).getDinero() - factura;
    		this.cuentasBancaria.get(id).setDinero(nuevaValorcCuenta);
    		return true;
    	}
    	else {
    		return false;
    	}
    
    }
    
}

