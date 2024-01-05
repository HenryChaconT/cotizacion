package com.jasper.service.impl;

import com.jasper.dto.CotizacionDetalleDto;
import com.jasper.dto.CotizacionDto;
import com.jasper.entity.Cotizacion;
import com.jasper.entity.CotizacionDetalle;
import com.jasper.exception.ResourceNotFoundException;
import com.jasper.repository.CotizacioDetalleRepository;
import com.jasper.repository.CotizacionRepository;
import com.jasper.service.CotizacionService;
import com.jasper.util.ReportGenerator;
import net.sf.jasperreports.engine.JRException;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
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
public class CotizacionServiceImpl implements CotizacionService {

    @Autowired
    private ReportGenerator reportGenerator;

    @Autowired
    private CotizacioDetalleRepository cotizacioDetalleRepository;

    @Autowired
    private CotizacionRepository cotizacionRepository;

    @Autowired
    private ModelMapper maper;

    @Override
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
        long ultimoId= listaCotizacion.stream().mapToLong(Cotizacion::getId).max().orElseThrow();
        CotizacionDto cotizacionDto=getById(ultimoId);
        int correlativo= Integer.parseInt(cotizacionDto.getCorrelativo())+1;
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


        //Guardar EN DASE DE DATOS
        cotizacionRepository.save(maper.map(list2,Cotizacion.class));
        list.forEach(listCorrelativo->listCorrelativo.setCorrelativo(correlativoString));
        List<CotizacionDetalle>ListaDetalleCotizacion= list.stream().map(listaDetalle->maper.map(listaDetalle,CotizacionDetalle.class)).toList();
        cotizacioDetalleRepository.saveAll(ListaDetalleCotizacion);

        return reportGenerator.exportToPdf(list,list2);
    }

    @Override
    public void save(CotizacionDto cotizacionDto) {

        List<Cotizacion> listaCotizacion=cotizacionRepository.findAll();
        CotizacionDto cotizacionDto1=getById(listaCotizacion.size());
        int correlativo= Integer.parseInt(cotizacionDto1.getCorrelativo()) + 1;
        String correlativoString = String.format("%07d", correlativo);

        cotizacionDto.setCorrelativo(correlativoString);

        cotizacionRepository.save(maper.map(cotizacionDto,Cotizacion.class));



        cotizacionDto.getCotizacionDetalleDtos().forEach(list->list.setCorrelativo(correlativoString));

        List<CotizacionDetalle> cotizacionDetalles=cotizacionDto.getCotizacionDetalleDtos().stream().map(list->maper.map(list,CotizacionDetalle.class)).toList();
        cotizacioDetalleRepository.saveAll(cotizacionDetalles);


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


