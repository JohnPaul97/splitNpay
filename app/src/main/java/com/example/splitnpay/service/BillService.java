package com.example.splitnpay.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.splitnpay.domain.Bill;
import com.example.splitnpay.domain.Split;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BillService {


    @RequiresApi(api = Build.VERSION_CODES.N)
    public Map<String, Double> cashoutBills(List<Bill> bills) {
        return bills.stream()
                .map(Bill::getSplits)
                .flatMap(Collection::stream)
                .map(this::cashoutSplit)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Double::sum));
    }

    private Map<String, Double> cashoutSplit(final Split split) {
        final Map<String, Double> toBePayed = new HashMap<>();

        final double amountPerBuyer = split.getTotal() / split.getBuyers().size();

        for (String buyer : split.getBuyers()) {
            toBePayed.put(buyer, amountPerBuyer);
        }

        return toBePayed;
    }
}
