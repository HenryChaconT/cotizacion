package com.jasper.controller;

import com.jasper.dto.CotizacionDetalleDto;
import com.jasper.dto.CotizacionDto;
import com.jasper.service.impl.CotizacionServiceImpl;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/coti")
public class CotizacionController {

    @Autowired
    private CotizacionServiceImpl cotizacionService;

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> exportPdf(@RequestBody CotizacionDto datos) throws JRException, FileNotFoundException {

        List<CotizacionDetalleDto> list=datos.getCotizacionDetalleDtos();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("cotizacion", "cotizacion.pdf");
        return ResponseEntity.ok().headers(headers).body(cotizacionService.exportPdf(datos.getCotizacionDetalleDtos(),datos));
    }
}
