package models;

import java.io.Serializable;

public class CuentaBancaria implements Serializable {

    private String identificacionDeCuenta;
    private double dinero;

    public CuentaBancaria(String identificacionDeCuenta, double dinero){
        this.identificacionDeCuenta = identificacionDeCuenta;
        this.dinero = dinero;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    public String getIdentificacionDeCuenta() {
        return identificacionDeCuenta;
    }

    public void setIdentificacionDeCuenta(String identificacionDeCuenta) {
        this.identificacionDeCuenta = identificacionDeCuenta;
    }

    public void agregarDinero(double dinero) {
        this.dinero = this.dinero + dinero;
    }
}