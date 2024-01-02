package com.jasper.util;

import com.jasper.dto.CotizacionDetalleDto;
import com.jasper.dto.CotizacionDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportGenerator {


    public byte[] exportToPdf(List<CotizacionDetalleDto> list, CotizacionDto list2) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReport(list,list2));
    }
    private JasperPrint getReport(List<CotizacionDetalleDto> list, CotizacionDto list2) throws FileNotFoundException, JRException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("Dc", new JRBeanCollectionDataSource(list));
        params.put("DirImagen","classpath:static/imagenes/");
        params.put("Id",list2.getCorrelativo());
        params.put("SubTotal",list2.getSubTotal());
        params.put("Descuento",list2.getTotalDescuento());
        params.put("Gravadas",list2.getVentasGravadas());
        params.put("Igv",list2.getIgv());
        params.put("ImporteTotal",list2.getImporteTotal());
        params.put("Ruc",list2.getRuc());
        params.put("Cliente",list2.getRazonSocial());
        params.put("Direccion",list2.getDireccion());
        params.put("Vencimiento",list2.getFechaVencimiento());
        params.put("PrecioTotalDes",list2.getDescripcionTotal());


        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:templates/report/Report.jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());

        return report;
    }
}
