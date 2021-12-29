package com.example.splitnpay.ui.main;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.splitnpay.domain.Bill;
import com.example.splitnpay.domain.Split;
import com.example.splitnpay.service.BillService;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Map<String, Double>> billsMap;
    private MutableLiveData<List<Bill>> billsList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<List<Bill>> getBillsList() {
        if (billsList == null) {
            billsList = new MutableLiveData<>();
            billsList.setValue(getBills());
        }

        billsList.getValue().forEach(System.out::println);

        return billsList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<Map<String, Double>> getBillsMap() {
        if (billsMap == null) {
            billsMap = new MutableLiveData<>();
            billsMap.setValue(getTotals());
        }

        return billsMap;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Bill> getBills() {
        final Split cazarePrimaNoapwte = new Split("cazare ziua 1", asList("Mark", "Andrei", "Paul", "Traian", "Ovi"), 300);
        final Split cazareadouaNoapte = new Split("cazare ziua 2", asList("Mark", "Andrei", "Paul"), 300);

        final Bill bill1 = new Bill("cazare", asList(cazarePrimaNoapwte, cazareadouaNoapte));

        final Split alimenteLaComun = new Split("comun", asList("Mark", "Andrei", "Paul", "Traian", "Ovi"), 1000);
        final Split separat1 = new Split("M+a", asList("Mark", "Andrei"), 30);
        final Split separat2 = new Split("M+p", asList("Mark", "Paul"), 75);
        final Split separat3 = new Split("T", asList("Traian"), 50);

        final Bill bill2 = new Bill("mancare", asList(alimenteLaComun, separat1, separat2, separat3));

        return asList(bill1, bill2);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Map<String, Double> getTotals() {

        final BillService billService = new BillService();

        return billService.cashoutBills(billsList.getValue());
    }
}