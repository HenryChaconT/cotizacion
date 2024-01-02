package com.jasper.service.impl;

import com.jasper.dto.CotizacionDetalleDto;
import com.jasper.dto.CotizacionDto;
import com.jasper.entity.Cotizacion;
import com.jasper.entity.CotizacionDetalle;
import com.jasper.exception.ResourceNotFoundException;
import com.jasper.repository.CotizacionRepository;
import com.jasper.util.ReportGenerator;
import net.sf.jasperreports.engine.JRException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class CotizacionServiceImpl {

    @Autowired
    private ReportGenerator reportGenerator;

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private ModelMapper maper;

    public byte[] exportPdf(List<CotizacionDetalleDto>  list, CotizacionDto list2) throws JRException, FileNotFoundException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        //DETALLE COTIZACION DTO LIST
        list.stream().peek(item -> item.setValorVenta(item.getValorUnitario() * item.getCantidad())).collect(Collectors.toList());
        list.stream().peek(item -> {
            item.setValorNetoProducto(item.getValorVenta() -(item.getValorVenta() * (0.01 * (Double.parseDouble(item.getDescuento())))));
            item.setValorNetoProducto(redondearADosDecimales(item.getValorNetoProducto()));
        }).collect(Collectors.toList());

        list.forEach(item -> item.setDescuento(item.getDescuento()+"%"));

        //COTIZACION DTO LIST2
        List<Cotizacion> listaCotizacion=cotizacionRepository.findAll();
        CotizacionDto cotizacionDto=getById(listaCotizacion.size());
        int correlativo= Integer.parseInt(cotizacionDto.getCorrelativo()) + 1;
        String correlativoString = String.format("%07d", correlativo);

        list2.setCorrelativo(correlativoString);

        //sumar todo los subtotal detalles
        List<Double> subTotalList=list.stream().map(CotizacionDetalleDto::getValorNetoProducto).toList();
        list2.setSubTotal(subTotalList.stream().mapToDouble(Double::doubleValue).sum());

        //sumar todo los descuentos detalles
        double subTotalFinal= list2.getSubTotal();
        List<Double> valorVentaList=list.stream().map(CotizacionDetalleDto::getValorVenta).toList();
        list2.setTotalDescuento(redondearADosDecimales(valorVentaList.stream().mapToDouble(Double::doubleValue).sum()-subTotalFinal));

        //total total ventas gravadas
        double gravada=redondearADosDecimales(subTotalList.stream().mapToDouble(Double::doubleValue).sum()-(valorVentaList.stream().mapToDouble(Double::doubleValue).sum()-subTotalFinal));
        list2.setVentasGravadas(gravada);

        //igv
        double igv=redondearADosDecimales(gravada*0.18);
        list2.setIgv(igv);



        //importe total
        list2.setImporteTotal(redondearADosDecimales(gravada+igv));

        return reportGenerator.exportToPdf(list,list2);
    }

    private static double redondearADosDecimales(double numero) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(numero));
    }

    public CotizacionDto getById(long id){
        Cotizacion cotizacion=cotizacionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("cotizacion","id",id));

        return maper.map(cotizacion,CotizacionDto.class);
    }





}


