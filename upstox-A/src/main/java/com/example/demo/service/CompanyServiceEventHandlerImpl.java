package com.example.demo.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceEventHandlerImpl implements CompanyServiceEventHandler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @KafkaListener(topics = "user-service-event")
    public void consume(String userStr) {
        try{
//           Product product = OBJECT_MAPPER.readValue(userStr, Product.class);
//            CompanyStage companyStage= new CompanyStage();
//            companyStage.setId(product.getCompanyId());
//            companyStage.setProductId(product.getId());
//            companyReposatory.findById(product.getCompanyId()).ifPresent(
//                    company -> {
//                        companyStage.setName(company.getName());
//                    }
//            );
//           companyStageReposatory.save(companyStage);
            String value = OBJECT_MAPPER.writeValueAsString(productDTO);
            this.kafkaTemplate.sendDefault(productDTO.getId(), value);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}