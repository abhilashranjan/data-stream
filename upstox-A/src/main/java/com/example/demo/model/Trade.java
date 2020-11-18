package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@JsonIgnoreProperties
public class Trade {
    @JsonProperty("sym")
    private String sym;
    @JsonProperty("T")
    private String type;
    @JsonProperty("P")
    private Double price;
    @JsonProperty("Q")
    private Double quantity;
    @JsonProperty("TS")
    private Double timeStampOne;
    @JsonProperty("side")
    private String side;
    @JsonProperty("TS2")
    private Long timeStampTwo;

    //    "sym": "XETHZUSD",
//            "T": "Trade",
//            "P": 226.85,
//            "Q": 0.02,
//            "TS": 1538409733.3449,
//            "side": "b",
//            "TS2": 1538409738828589281
}
