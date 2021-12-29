package com.example.splitnpay.domain;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Bill implements Serializable {
    private String name;
    private List<Split> splits;
    private Double total;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Bill(final String name, final List<Split> splits) {
        this.name = name;
        this.splits = splits;

        updateTotal();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addSplit(final Split split) {
        this.splits.add(split);
        updateTotal();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateTotal() {
        total = splits.stream()
                .map(Split::getTotal)
                .reduce((double) 0, Double::sum);
    }
}
