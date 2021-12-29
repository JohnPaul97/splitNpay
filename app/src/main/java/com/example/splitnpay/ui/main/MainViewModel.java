package com.example.splitnpay.ui.main;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.splitnpay.domain.Bill;
import com.example.splitnpay.domain.Split;
import com.example.splitnpay.repository.BillRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    private final BillRepository billRepository;

    public MainViewModel() {
        this.billRepository = BillRepository.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addBill(final String billName, final Split split) {
        billRepository.addBill(billName, split);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<List<Bill>> getBillsList() {
        return billRepository.getBills();
    }
}