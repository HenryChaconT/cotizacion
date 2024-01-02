package com.jasper.service.impl;


import com.jasper.dto.RucDataDto;
import com.jasper.exception.CotizaAPIException;
import com.jasper.exception.ResourceNotFoundException;
import com.jasper.service.ConsultasService;
import com.jasper.util.Urls;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ConsultasServiceImpl implements ConsultasService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModelMapper mapper;


    @Override
    public RucDataDto getByRuc(String ruc) {
        /**
         Map response=restTemplate.getForObject(Urls.URL_BASE_RUC+ruc+"?api_token="+Urls.TOKEN,Map.class);

         assert response != null;
         if (!response.isEmpty()){
         RucDto rucDto=mapper.map(response,RucDto.class);
         return rucDto.getData();
         }
         else {
         throw new ResourceNotFoundException("Ruc","nuemro",Long.parseLong(ruc));
         }**/


        HttpHeaders headers = new HttpHeaders();
        headers.set("Referer", Urls.URL_BASE_RUC + "?numero=" + ruc);
        headers.set("Authorization", "Bearer " + Urls.TOKEN);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                Urls.URL_BASE_RUC + "?numero=" + ruc,
                HttpMethod.GET,
                entity,
                Map.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            Map response = responseEntity.getBody();

            assert response != null;
            if (!response.isEmpty()) {
                RucDataDto rucdataDto = mapper.map(response, RucDataDto.class);
                return rucdataDto;

            } else {
                throw new ResourceNotFoundException("Ruc", "numero", Long.parseLong(ruc));
            }
        }else {
            throw new CotizaAPIException(HttpStatus.INTERNAL_SERVER_ERROR,"error interno");
        }

    }
}
