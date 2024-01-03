package com.jasper.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class CotizacionDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String correlativo;
    @Column
    private String codigo;
    @Column
    private String descripcion;
    @Column
    private String unidad;
    @Column
    private int cantidad;
    @Column
    private double valorUnitario;
    @Column
    private double valorVenta;
    @Column
    private String descuento;
    @Column
    private double valorNetoProducto;
}
