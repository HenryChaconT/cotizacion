package com.jasper.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CotizacionDetalleDto {

    private long id;
    private String codigo;
    private String descripcion;
    private String unidad;
    private int cantidad;
    private double valorUnitario;
    private double valorVenta;
    private String descuento;
    private double valorNetoProducto;




}
