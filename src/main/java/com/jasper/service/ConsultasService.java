package com.jasper.service;


import com.jasper.dto.RucDataDto;

public interface ConsultasService {

    RucDataDto getByRuc(String ruc) throws Exception;
}
