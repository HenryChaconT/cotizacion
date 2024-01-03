package com.jasper.service;

import com.jasper.dto.CotizacionDetalleDto;
import com.jasper.dto.CotizacionDto;
import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;
import java.util.List;

public interface CotizacionService {

    byte[] exportPdf(List<CotizacionDetalleDto> list, CotizacionDto list2) throws JRException, FileNotFoundException;

<<<<<<< HEAD
    void save(CotizacionDto cotizacionDto);
=======
    CotizacionDto saveCotizacion(CotizacionDto cotizacionDto);

    CotizacionDto getById(long id);
>>>>>>> fe381c97e6c2f1b796b6fc073af974d7180c8b1b
}
