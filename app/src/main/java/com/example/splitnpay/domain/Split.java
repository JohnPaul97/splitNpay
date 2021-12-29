package com.example.splitnpay.domain;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Split implements Serializable {
    private String name;
    private List<String> buyers;
    private double total;
}
