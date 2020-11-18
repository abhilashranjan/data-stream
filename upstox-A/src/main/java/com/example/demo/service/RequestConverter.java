package com.example.demo.service;

import com.example.demo.model.Trade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestConverter {

    Trade convertJsonToTrade(String request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = "{\"sym\":\"XETHZUSD\", \"T\":\"Trade\", \"P\":226.85, \"Q\":4.98,\"TS\":1538409733.3502, \"side\": \"b\", \"TS2\":1538409738828643608}";
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        Trade trade = objectMapper.readValue(request, new TypeReference<Trade>() {});
        System.out.println(trade);
        return trade;
    }
}
