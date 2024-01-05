package com.jasper.service;

import com.jasper.dto.CotizacionDetalleDto;
import com.jasper.dto.CotizacionDto;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface CotizacionService {

    byte[] exportPdf(List<CotizacionDetalleDto> list, CotizacionDto list2) throws JRException, FileNotFoundException;

    void save(CotizacionDto cotizacionDto);
}
