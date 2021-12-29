package com.example.splitnpay.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.splitnpay.domain.Bill;
import com.example.splitnpay.domain.Split;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class BillRepository {

    private static BillRepository instance;

    private BillRepository() {
    }

    public static BillRepository getInstance() {
        if (instance == null) {
            instance = new BillRepository();
        }

        return instance;
    }

    private MutableLiveData<List<Bill>> bills;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void fetchListFromCache(final List<Bill> cachedBills) {
        getBills();
        bills.setValue(cachedBills);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<List<Bill>> getBills() {
        if (bills == null) {
            bills = new MutableLiveData<>();
//            bills.setValue(mockBills());
        }

//        bills.getValue().forEach(System.out::println);

        return bills;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Bill addBill(final String billName, final Split split) {
        final Bill bill = findByName(billName)
                .orElse(new Bill(billName, new ArrayList<>()));
        bill.addSplit(split);

        final List<Bill> newBills = bills.getValue();
        newBills.remove(bill);
        newBills.add(bill);

        bills.setValue(newBills);

        return bill;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Optional<Bill> findByName(final String name) {
        return bills.getValue()
                .stream()
                .filter(bill -> name.equals(bill.getName()))
                .findFirst();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Bill> mockBills() {
        final Split cazarePrimaNoapwte = new Split("cazare ziua 1", asList("Mark", "Andrei", "Paul", "Traian", "Ovi"), 300);
        final Split cazareadouaNoapte = new Split("cazare ziua 2", asList("Mark", "Andrei", "Paul"), 300);

        final Bill bill1 = new Bill("cazare", new ArrayList<>(asList(cazarePrimaNoapwte, cazareadouaNoapte)));

        final Split alimenteLaComun = new Split("comun", asList("Mark", "Andrei", "Paul", "Traian", "Ovi"), 1000);
        final Split separat1 = new Split("M+a", asList("Mark", "Andrei"), 30);
        final Split separat2 = new Split("M+p", asList("Mark", "Paul"), 75);
        final Split separat3 = new Split("T", asList("Traian"), 50);

        final Bill bill2 = new Bill("mancare", new ArrayList<>(asList(alimenteLaComun, separat1, separat2, separat3)));

        return new ArrayList<>(asList(bill1, bill2));
    }
}
