package com.jasper.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cotizacion",uniqueConstraints = {@UniqueConstraint(columnNames = {"correlativo"})})
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String correlativo;
    @Column
    private String ruc;
    @Column
    private String razonSocial;
    @Column
    private String direccion;
    @Column
    private Date fechaVencimiento;
    @Column
    private double subTotal;
    @Column
    private double totalDescuento;
    @Column
    private double ventasGravadas;
    @Column
    private double igv;
    @Column
    private double importeTotal;
    @Column
    private String descripcionTotal;
}
