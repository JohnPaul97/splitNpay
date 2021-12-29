package com.example.splitnpay.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Split {
    private String name;
    private List<String> buyers;
    private double total;
}
