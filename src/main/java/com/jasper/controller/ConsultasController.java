package com.jasper.controller;


import com.jasper.service.impl.ConsultasServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsultasController {

    @Autowired
    private ConsultasServiceImpl consultasService;

    @GetMapping("/{ruc}")
    public ResponseEntity<?> getRuc(@PathVariable(name = "ruc") String ruc) throws Exception {


        return new ResponseEntity<>(consultasService.getByRuc(ruc), HttpStatus.OK);
    }
}
