package com.jasper.dto;

import lombok.*;

import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CotizacionDto {

    private long id;
    private String correlativo;
    private String ruc;
    private String razonSocial;
    private String direccion;
    private Date fechaVencimiento;
    private double subTotal;
    private double totalDescuento;
    private double ventasGravadas;
    private double igv;
    private double importeTotal;
    private String descripcionTotal;
    private List<CotizacionDetalleDto> cotizacionDetalleDtos;
}
